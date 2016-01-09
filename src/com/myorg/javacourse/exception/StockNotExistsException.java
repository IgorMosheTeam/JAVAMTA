package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockNotExistsException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	
	public StockNotExistsException (){
		super("The stock is not in the portfolio!");
	}
}
