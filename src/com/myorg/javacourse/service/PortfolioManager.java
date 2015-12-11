package com.myorg.javacourse.service;

import com.myorg.javacourse.*;
import com.myorg.javacourse.model.*;

public class PortfolioManager {
	
	private final static int ALL = -1;
	
	public Portfolio getPortfolio() {
		Portfolio portfolio = new Portfolio();
		portfolio.setTitle("Exercise 7 portfolio");
		portfolio.updateBalalnce(10000);

		Stock stock1 = new Stock();
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 10.0);
		stock1.setBid((float) 8.5);
		stock1.setDate("12/15/2014");
		portfolio.buyStock(stock1, 20);
		
		Stock stock2 = new Stock();
		stock2.setSymbol("AAL");
		stock2.setAsk((float) 30.0);
		stock2.setBid((float) 25.5);
		stock2.setDate("12/15/2014");
		portfolio.buyStock(stock2, 30);
		
		Stock stock3 = new Stock();
		stock3.setSymbol("CAAS");
		stock3.setAsk((float) 20.0);
		stock3.setBid((float) 15.5);
		stock3.setDate("12/15/2014");
		portfolio.buyStock(stock3, 40);
		
		portfolio.sellStock("AAL", ALL);
		portfolio.removeStock("CAAS");
		
		return portfolio;
	}
}
