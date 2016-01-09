package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class IllegalQuantityException extends PortfolioException {

	private static final long serialVersionUID = 1L;
	private static final int ALL = -1;
	
	public IllegalQuantityException (){
		super("Illigal quantity! Should be possitive or " + ALL + ".");
	}
}