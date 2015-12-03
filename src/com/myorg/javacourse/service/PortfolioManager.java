package com.myorg.javacourse.service;

import com.myorg.javacourse.*;
import com.myorg.javacourse.model.*;

public class PortfolioManager {
	
	public Portfolio getPortfolio() {
		Portfolio portfolio = new Portfolio();

		Stock stock1 = new Stock();
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 13.1);
		stock1.setBid((float) 12.4);
		//stock1.setDate("11/15/2014");
		portfolio.setStock(stock1);
		
		Stock stock2 = new Stock();
		stock2.setSymbol("AAL");
		stock2.setAsk((float) 5.78);
		stock2.setBid((float) 5.5);
		//stock2.setDate("11/15/2014");
		portfolio.setStock(stock2);
		
		Stock stock3 = new Stock();
		stock3.setSymbol("CAAS");
		stock3.setAsk((float) 32.2);
		stock3.setBid((float) 31.5);
		//stock3.setDate("11/15/2014");
		portfolio.setStock(stock3);
		
		return portfolio;
	}
	
	public Portfolio getPortfolio(Portfolio portfolio) {
		Portfolio newPortfolio = new Portfolio(portfolio);
		return newPortfolio;
	}
}
