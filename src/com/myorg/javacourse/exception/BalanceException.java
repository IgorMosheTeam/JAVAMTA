package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class BalanceException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	
	public BalanceException (){
		super("Not enough money to complete operation!");
	}
}
