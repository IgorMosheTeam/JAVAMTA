package com.myorg.javacourse.model;

import com.myorg.javacourse.*;

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
	
	public void editStock(int stockNum, String act, String symbol) {
		this.stocks[stockNum - 1].setSymbol(symbol);
	}
	
	public void editStock(int stockNum, String act, float num) {
		if (act == "bid") {
			this.stocks[stockNum - 1].setAsk(num);	
		}
		
		else {
			this.stocks[stockNum - 1].setBid(num);
		}
	}
	
	
	public void removeStock(int stockNum) {
		for (int i = stockNum - 1; i < this.portfolioSize - 1; i++) {
			this.stocks[i] = this.stocks[i + 1];
		}
		this.stocks[portfolioSize - 1] = null;
		this.portfolioSize--;
	}
	
	public String getHtmlString()
	{
		String portfolioStr = new String("<h1>" + title + "</h1>");
		
		for (int i = 0; i < portfolioSize; i++) {
			portfolioStr = portfolioStr.concat(stocks[i].getHtmlDescription() + "<br>");
		}

		return portfolioStr;
	}	
}
