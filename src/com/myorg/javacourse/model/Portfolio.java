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
	private String title = "Igor's Portfolio";
	private final static int MAX_PORTFOLIO_SIZE = 5;
	private int portfolioSize = 0;
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
	}
	
	public void setTitle(String string) {
		title = string;
	}
	
	public void setStock(Stock stockToAdd) {
		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;
	}

	public Stock[] getStocks() {
		return stocks;
	}
	
	/**
	 * Edits stock's symbol
	 * @param stockNum
	 * @param act
	 * @param symbol
	 */
	public void editStock(int stockNum, String act, String symbol) {
		this.stocks[stockNum - 1].setSymbol(symbol);
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
	public void removeStock(int stockNum) {
		for (int i = stockNum - 1; i < this.portfolioSize - 1; i++) {
			this.stocks[i] = this.stocks[i + 1];
		}
		this.stocks[portfolioSize - 1] = null;
		this.portfolioSize--;
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
