package com.myorg.javacourse.service;

import java.util.ArrayList;
import java.util.List;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;

import com.myorg.javacourse.model.*;

public class PortfolioManager implements PortfolioManagerInterface{

	private final static int ALL = -1;
	public enum ALGO_RECOMMENDATION {BUY, SELL, REMOVE, HOLD};
	Portfolio portfolio;

	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}

	private PortfolioInterface fromDto(PortfolioDto portfolioDto) {
		StockDto[] stocks = portfolioDto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray);
		}

		ret.setTitle(portfolioDto.getTitle());
		try {
			((PortfolioManagerInterface) ret).updateBalance(portfolioDto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private DatastoreService datastoreService = ServiceManager.datastoreService();

	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		List<StockDto> stocksList = null;
		
		try {
			stocksList = MarketService.getInstance().getStocks(symbols);
		} catch (SymbolNotFoundInNasdaq e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (StockDto stockDto : stocksList) {
			Stock stock = fromDto(stockDto);
			currentStocksList.add(stock);
		}

		for (Stock stock : currentStocksList) {
			update.add(new Stock(stock));
		}

		datastoreService.saveToDataStore(toDtoList(update));
	}

	public void setTitle(String title) {
		PortfolioDto portfolio = (PortfolioDto) getPortfolio();
		((PortfolioManagerInterface) portfolio).setTitle(title);
		datastoreService.updatePortfolio(portfolio);
		
		flush(portfolio);
	}

	/**
	 * update database with new portfolio's data
	 * @param portfolio
	 */
	private void flush(PortfolioDto portfolio) {
		datastoreService.updatePortfolio(portfolio);
	}

	public void updateBalance(float value) throws PortfolioException {
		PortfolioDto portfolio = (PortfolioDto) getPortfolio();
		((PortfolioManagerInterface) portfolio).updateBalance(value);
		datastoreService.updatePortfolio(portfolio);
		
		flush(portfolio);
	}
	
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

	public PortfolioTotalStatus[] getPortfolioTotalStatus() {
		return null;
	}


	public void addStock(String symbol) throws PortfolioException {

	}

	public void buyStock(String symbol, int quantity) throws PortfolioException {

	}

	public void sellStock(String symbol, int quantity)
			throws PortfolioException {

	}

	public void removeStock(String symbol) throws PortfolioException {

	}
	
	/**
	 * fromDto - get stock from Data Transfer Object
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate().getTime());
		newStock.setStockQuantity(stockDto.getQuantity());
		if(stockDto.getRecommendation() != null) newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));

		return newStock;
	}
	
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}
}
