package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class PortfolioFullException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	
	public PortfolioFullException (){
		super("The portfolio is full!");
	}
}
