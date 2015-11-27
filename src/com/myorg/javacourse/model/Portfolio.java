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
	
	public void setStock(Stock stockToAdd) {
		stocks[portfolioSize] = stockToAdd;
		portfolioSize++;
	}

	public Stock[] getStocks() {
		return stocks;
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
