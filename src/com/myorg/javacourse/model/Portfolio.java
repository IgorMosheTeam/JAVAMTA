package com.myorg.javacourse.model;

import com.myorg.javacourse.*;

/**
* This class built to represent a portfolio of stocks.
* It contains the portfolio title(String), Portfolio max size(int), portfolio cur size(int) and array of stocks(Stock).
* The class has two c'tors:
* 	1. public Portfolio() - creates a new portfolio object.
* 	2. public Portfolio(Portfolio portfolio) - creates a copy of a portfolio object.
* The class supports the following methods:
* 	1. public void editStock() - edit one of the stocks in the portfolio.
* 	1. public void removeStock(int stockNum) - removes a stock from the portfolio.
* 	2. public String getHtmlString() - produces a string with a list of stocks in the portfolio.
* 
* @param Portfolio
* @returns Portfolio
*/

public class Portfolio {
	public enum ALGO_RECOMMENDATION {BUY, SELL, REMOVE, HOLD};	
	private final static int ALL = -1;
	private final static int MAX_PORTFOLIO_SIZE = 5;
	private String title = "Igor's Portfolio";
	private int portfolioSize = 0;
	private float balance = 0;
	Stock stocks[];
	
	public Portfolio() {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
	
	public Portfolio(Portfolio portfolio) {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		for (int i = 0; i < portfolio.portfolioSize; i++) {
			this.stocks[i] = new Stock(portfolio.stocks[i]);
		}
		this.portfolioSize = portfolio.portfolioSize;
		this.balance = portfolio.balance;
	}
	
	public void setTitle(String string) {
		title = string;
	}
	
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

	public Stock[] getStocks() {
		return stocks;
	}
	
	public void updateBalalnce(float money) {
		if (balance + money >= 0) {
			balance += money;
			System.out.println("Balance successfuly updated");
		}
		
		else {
			System.out.println("Operation failed! You have less than " + money + " at your blance");
		}
	}
	
	/**
	 * Edits stock's members ask or bid.
	 * @param stockNum
	 * @param act
	 * @param num
	 */
	public void editStock(int stockNum, String act, float num) {
		if (act == "bid") {
			this.stocks[stockNum - 1].setAsk(num);	
		}
		
		else {
			this.stocks[stockNum - 1].setBid(num);
		}
	}
	
	/**
	 * Removes stock from the portfolio.
	 * @param stockNum
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
				System.out.println("The stock removed from the portfolio.");
				return true;
			}
		}
		return false;
	}
	
	public boolean sellStock(String stockSymbol, int quantity) {
		if (quantity == 0) {
			System.out.println("Can't sell 0 stocks!");
			return false;
		}
			
		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stockSymbol)) {
				if (stocks[i].getStockQuantity() < quantity) {
					System.out.println("Not enough stocks to sell!");
					return false;
				}
				
				else {
					if (quantity == ALL) {
						quantity = stocks[i].getStockQuantity();
					}
					
					balance += quantity * stocks[i].getBid();
					stocks[i].updateStockQuantity(-quantity);
					System.out.println("The stock had been sold.");
					return true;
				}
			}
		}
		System.out.println("The stock is not in the portfolio!");
		return false;
	}
	
	public boolean buyStock(Stock stock, int quantity) {
		int i;
		
		if (quantity == ALL) {
			quantity = (int)(balance / stock.getAsk());
		}
			
		if (quantity * stock.getAsk() > balance || stock.getAsk() > balance) {
			System.out.println("Not enough balance to complete purchase!");
			return false;
		}
			
		for (i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(stock.getSymbol())) {
				stocks[i].updateStockQuantity(quantity);
				balance -= quantity * stock.getAsk();
				System.out.println("Stocks successfuly purchased.");
				return true;
			}
		}
		
		addStock(stock);
		
		if (i ==  MAX_PORTFOLIO_SIZE) {
			return false;
		}
		
		stocks[i].updateStockQuantity(quantity);
		balance -= quantity * stock.getAsk();
		System.out.println("Stocks successfuly purchased.");
		return true;
	}
	
	/**
	 * Produces a string with a list of stocks in the portfolio.
	 * @return String
	 */
	public String getHtmlString()
	{
		String portfolioStr = new String("<h1>" + title + "</h1>");
		for (int i = 0; i < portfolioSize; i++) {
			portfolioStr = portfolioStr.concat(stocks[i].getHtmlDescription() + "<br>");
		}
		return portfolioStr;
	}	
}
