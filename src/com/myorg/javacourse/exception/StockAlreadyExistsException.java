package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockAlreadyExistsException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	
	public StockAlreadyExistsException (){
		super("The portfolio is full!");
	}
}
