package com.myorg.javacourse.model;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import com.myorg.javacourse.*;

/**
* This class built to represent a portfolio of stocks.
* It contains the portfolio title(String), portfolio max size(int), portfolio cur size(int), array of stocks(Stock), money balance(float).
* The class has two c'tors:
* 	1. public Portfolio() - creates a new portfolio object.
* 	2. public Portfolio(Portfolio portfolio) - creates a copy of a portfolio object.
* The class supports the following methods:
* 	1. Getters and setters for title and balance. 
* 	2. public void addStock(Stock stockToAdd) - adds a stock to portfolio.
* 	3. public void updateValue(String stockSymbol, String act, float num) - edits bid or ask of the given stock.
* 	4. public boolean buyStock(Stock stock, int quantity) - buys an ammount of the given stock.
* 	5. public boolean removeStock(String stockSymbol) - sells all the stock and removes it from the portfolio.
* 	6. public boolean sellStock(String stockSymbol, int quantity) - Sells an ammount of the given stock;
* 	3. public String getHtmlString() - produces a string with a list of stocks in the portfolio.
* 	4. public float getStocksValue() - returns the total value of all the stocks in the portfolio.
* 	5. public float getTotalValue() - returns the total value of the portfolio (stocks + balance).
* 	6.
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
	
	public void updateBalance(float money) {
		if (balance + money >= 0) {
			balance += money;
			System.out.println("Balance successfuly updated with " + money);
		}
		
		else {
			System.out.println("Operation failed! You have less than " + money + " at your blance");
		}
	}
	
	/**
	 * Adds a stock to portfolio.
	 * @param stockToAdd
	 */
	public void addStock(Stock stockToAdd) {
		if (portfolioSize == MAX_PORTFOLIO_SIZE) {
			System.out.println("Can't add new stock, portfolio can only have " + MAX_PORTFOLIO_SIZE + " stocks.");
			return;
		}
		
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockToAdd.getSymbol())) {
				return;
			}
		}
		
		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;
	}
	
	/**
	 * Edits stock's members ask or bid.
	 * @param stockNum
	 * @param act
	 * @param num
	 */
	public void updateValue(String stockSymbol, String act, float num) {
		if (num <= 0) {
			System.out.println("Wrong value! Should be greater than 0.");
			return;
		}
		
		if (act != "bid" && act != "ask") {
			System.out.println("Wrong operation! Should be 'ask' or 'bid'.");
			return;
		}
		
		Stock stock = null;
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockSymbol)) {
				stock = (Stock) stocks[i];
				break;
			}
		}
		
		if (stock == null) {
			System.out.println("The stock not found int the portfolio!");
			return;
		}
		
		else if (act == "bid") {
			stock.setBid(num);	
		}
		
		else {
			stock.setAsk(num);
		}
	}
	
	/**
	 * Buys an ammount of the given stock. Returns boolean value to indicate success or failure of the operation.
	 * -1 can be used to buy to buy as many stocks as possible.  
	 * @param stock
	 * @param quantity
	 */
	public boolean buyStock(Stock stock, int quantity) {
		int i;
		
		if (quantity <= 0 && quantity != ALL) {
			System.out.println("Invalid quantity!");
			return false;
		}

		if (quantity == ALL) {
			quantity = (int)(balance / stock.getAsk());
		}
			
		if (quantity * stock.getAsk() > balance || stock.getAsk() > balance) {
			System.out.println("Not enough balance to complete purchase!");
			return false;
		}
			
		for (i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stock.getSymbol())) {
				((Stock) stocks[i]).updateStockQuantity(quantity);
				updateBalance(-quantity * stock.getAsk());
				System.out.println("Stocks successfuly purchased.");
				return true;
			}
		}
		
		addStock(stock);
		
		if (i ==  MAX_PORTFOLIO_SIZE) {
			return false;
		}
		
		((Stock) stocks[i]).updateStockQuantity(quantity);
		updateBalance(-quantity * stock.getAsk());
		System.out.println("Stocks successfuly purchased.");
		return true;
	}
	
	/**
	 * Sells all the stock and removes it from the portfolio. Returns boolean value to indicate success or failure of the operation.
	 * Uses sellStock(String stockSymbol, int quantity) method to sell the stock before removing.
	 * @param stockSymbol
	 */
	public boolean removeStock(String stockSymbol) {
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockSymbol)) {
				sellStock(stockSymbol, ALL);
				
				for (int j = i; j < portfolioSize - 1; j++) {
					stocks[j] = stocks[j + 1];
				}
				
				stocks[portfolioSize - 1] = null;
				portfolioSize--;
				System.out.println("The stock had been removed from the portfolio.");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sells an ammount of the given stock. Returns boolean value to indicate success or failure of the operation.
	 * -1 can be used to sell all the quantity of the stocks. 
	 * @param stockSymbol
	 * @param quantity
	 */
	public boolean sellStock(String stockSymbol, int quantity) {
		if (quantity <= 0 && quantity != ALL) {
			System.out.println("Invalid quatity!");
			return false;
		}
			
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockSymbol)) {
				if (((Stock) stocks[i]).getStockQuantity() < quantity) {
					System.out.println("Not enough stocks to sell!");
					return false;
				}
				
				else {
					if (quantity == ALL) {
						quantity = ((Stock) stocks[i]).getStockQuantity();
					}
					
					updateBalance(quantity * stocks[i].getBid());
					((Stock) stocks[i]).updateStockQuantity(-quantity);
					System.out.println("The stock had been sold.");
					return true;
				}
			}
		}
		System.out.println("The stock is not in the portfolio!");
		return false;
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
	
	public StockInterface findStock(String symbol) {
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(symbol)) {
				return stocks[i];
			}
		}
		
		System.out.println("Stock Not Exists: " + symbol);
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
