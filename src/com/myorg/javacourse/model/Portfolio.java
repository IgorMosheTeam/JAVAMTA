package com.myorg.javacourse.model;

import org.algo.exception.PortfolioException;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

import com.myorg.javacourse.*;
import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.IllegalQuantityException;
import com.myorg.javacourse.exception.NotEnoughStocksException;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistsException;
import com.myorg.javacourse.exception.StockNotExistsException;

/**
 * This class built to represent a portfolio of stocks.
 * It contains the portfolio title(String), portfolio max size(int), portfolio cur size(int), array of stocks(Stock), money balance(float).
 * The class has two c'tors:
 * 	1. public Portfolio() - creates a new portfolio object.
 * 	2. public Portfolio(Portfolio portfolio) - creates a copy of a portfolio object.
 * 	3. public Portfolio(Stock[] stockArray) - creates a new instance of a portfolio object with a copy of stocks array.
 * The class supports the following methods:
 * 	1. Getters and setters for title and balance. 
 * 	2. public void addStock(Stock stockToAdd) - adds a stock to portfolio.
 * 	3. public void buyStock(Stock stock, int quantity) - buys an ammount of the given stock.
 * 	4. public void removeStock(String stockSymbol) - sells all the stock and removes it from the portfolio.
 * 	5. public void sellStock(String stockSymbol, int quantity) - Sells an ammount of the given stock;
 * 	6. public String getHtmlString() - produces a string with a list of stocks in the portfolio.
 * 	7. public float getStocksValue() - returns the total value of all the stocks in the portfolio.
 * 	8. public float getTotalValue() - returns the total value of the portfolio (stocks + balance).
 * 	9.public StockInterface findStock(String symbol) - finds a stock objectin portfolio based on symbol and returns it
 * @param Portfolio
 * @returns Portfolio
 */

public class Portfolio implements PortfolioInterface {
	private final static int ALL = -1;
	private final static int MAX_PORTFOLIO_SIZE = 5;
	private String title = "Igor's Portfolio";
	private int portfolioSize = 0;
	private float balance = 0;
	private StockInterface stocks[];

	public Portfolio() {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}

	public Portfolio(Stock[] stockArray) {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portfolioSize = stockArray.length;
		for (int i = 0; i < portfolioSize; i++) {
			this.stocks[i] = stockArray[i];
		}
	}

	public Portfolio(Portfolio portfolio) {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		for (int i = 0; i < portfolio.portfolioSize; i++) {
			this.stocks[i] = new Stock((Stock) portfolio.stocks[i]);
		}
		this.portfolioSize = portfolio.portfolioSize;
		this.balance = portfolio.balance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String string) {
		title = string;
	}

	public static int getMaxSize() {
		return MAX_PORTFOLIO_SIZE;
	}

	public StockInterface[] getStocks() {
		return (StockInterface[]) stocks;
	}

	public int getPortfolioSize() {
		return portfolioSize;
	}

	public float getBalance() {
		return balance;
	}


	public void updateBalance(float money) throws BalanceException {
		if (balance + money >= 0) {
			balance += money;
		}

		else {
			throw new BalanceException();
		}
	}

	/**
	 * Adds a stock to portfolio.
	 * @param stockToAdd
	 */
	public void addStock(Stock stockToAdd) throws PortfolioFullException, StockAlreadyExistsException {
		if (portfolioSize == MAX_PORTFOLIO_SIZE) {
			throw new PortfolioFullException();
		}

		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockToAdd.getSymbol())) {
				throw new StockAlreadyExistsException();
			}
		}

		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;
	}

	/**
	 * d an ammount of the given stock. Throws exception in case of failure of the operation.
	 * -1 can be used to buy to buy as many stocks as possible.  
	 * @param stock
	 * @param quantity
	 * @throws BalanceException 
	 * @throws IllegalQuantityException 
	 * @throws PortfolioFullException 
	 * @throws StockAlreadyExistsException 
	 */
	public void buyStock(Stock stock, int quantity) throws PortfolioException {
		int i;

		if (quantity <= 0 && quantity != ALL) {
			throw new IllegalQuantityException();
		}

		if (quantity == ALL) {
			quantity = (int)(balance / stock.getAsk());
		}

		if (quantity * stock.getAsk() > balance || stock.getAsk() > balance) {
			throw new BalanceException();
		}

		for (i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stock.getSymbol())) {
				((Stock) stocks[i]).updateStockQuantity(quantity);
				updateBalance(-quantity * stock.getAsk());
				return;
			}
		}

		try {
			addStock(stock);
		} catch (PortfolioFullException | StockAlreadyExistsException e) {
			throw e;
		}

		((Stock) stocks[i]).updateStockQuantity(quantity);
		updateBalance(-quantity * stock.getAsk());
	}

	/**
	 * Sells all the stock and removes it from the portfolio. Throws exception in case of failure of the operation.
	 * Uses sellStock(String stockSymbol, int quantity) method to sell the stock before removing.
	 * @param stockSymbol
	 * @throws PortfolioException 
	 */
	public void removeStock(String stockSymbol) throws PortfolioException {
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockSymbol)) {
				try {
					sellStock(stockSymbol, ALL);
				} catch (IllegalQuantityException | NotEnoughStocksException | BalanceException e) {
					throw e;
				}

				for (int j = i; j < portfolioSize - 1; j++) {
					stocks[j] = stocks[j + 1];
				}

				stocks[portfolioSize - 1] = null;
				portfolioSize--;
				return;
			}
		}
		
		throw new StockNotExistsException();
	}

	/**
	 * Sells an ammount of the given stock. Throws exception in case of failure of the operation.
	 * -1 can be used to sell all the quantity of the stocks. 
	 * @param stockSymbol
	 * @param quantity
	 * @throws IllegalQuantityException
	 * @throws NotEnoughStocksException
	 * @throws StockNotExistsException
	 * @throws BalanceException 
	 */
	public void sellStock(String stockSymbol, int quantity) throws IllegalQuantityException, NotEnoughStocksException, StockNotExistsException, BalanceException {
		if (quantity <= 0 && quantity != ALL) {
			throw new IllegalQuantityException();
		}

		Stock stock = (Stock) findStock(stockSymbol);

		if (stock == null) {
			throw new StockNotExistsException();
		}

		else {
			if (quantity == ALL) {
				quantity = (stock.getStockQuantity());
			}
			
			if (stock.getStockQuantity() < quantity) {
				throw new NotEnoughStocksException();
			}

			try {
				updateBalance(quantity * stock.getBid());
			} catch (BalanceException e) {
				throw e;
			}
			
			stock.updateStockQuantity(-quantity);
		}
	}

	/**
	 * Returns the total value (float) of all the stocks in the portfolio. 
	 */
	public float getStocksValue() {
		int value = 0;
		for (int i = 0; i < portfolioSize; i++) {
			value += ((Stock) stocks[i]).getStockQuantity() * stocks[i].getBid();
		}
		return value;
	}

	/**
	 * Returns the total value (float) of the portfolio (stocks + balance). 
	 */
	public float getTotalValue() {
		return getStocksValue() + getBalance();
	}

	/**
	 * Finds a stock object in portfolio based on symbol and returns it
	 * @param symbol
	 * @return Stock
	 */
	public StockInterface findStock(String symbol) {
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(symbol)) {
				return stocks[i];
			}
		}

		return null;
	}


	/**
	 * Produces a string with portfolio total value, stocks total value and blance, and a list of all the stocks in the portfolio.
	 * @return String
	 */
	public String getHtmlString()
	{
		String portfolioStr = new String("<h1>" + title + "</h1>");
		portfolioStr = portfolioStr.concat("<b>Total portfolio value</b>: " + getTotalValue() + "$, <b>total stocks value</b>: " + getStocksValue() + "$, <b>balance</b>: " + getBalance() + "$.<br><br><b><u>Stocks in the portfolio:</u></b><br>");
		for (int i = 0; i < portfolioSize; i++) {
			portfolioStr = portfolioStr.concat(((Stock) stocks[i]).getHtmlDescription() + "<br>");
		}
		return portfolioStr;
	}
}
