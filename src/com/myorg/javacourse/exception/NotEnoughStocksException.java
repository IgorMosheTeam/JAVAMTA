package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class NotEnoughStocksException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	
	public NotEnoughStocksException (){
		super("Cannot sell more stocks than you have!");
	}
}