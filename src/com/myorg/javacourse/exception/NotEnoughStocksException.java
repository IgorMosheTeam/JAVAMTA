package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class NotEnoughStocksException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	private static final int ALL = -1;
	
	public NotEnoughStocksException (){
		super("Can't sell! It's more stocks than you have!");
	}
}