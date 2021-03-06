package com.myorg.javacourse.model;

import java.util.*;
import java.text.*;
import java.lang.Object;
import org.algo.model.StockInterface;
import com.myorg.javacourse.model.Portfolio.*;
import com.myorg.javacourse.service.PortfolioManager.ALGO_RECOMMENDATION;

/**
* This class built to represent a stock.
* It contains the stock's symbol(String), ask(float), bid(float), date(Date) and quantity(int).
* The class has 3 c'tors:
* 	1. public Stock() - creates a new stock object.
* 	2. public Stock(Stock stock) - creates a copy of a stock object.
* 	3. public Stock(String newSymbol, float newAsk, float newBid) - creates a copy of a stock object' using only specific members
* The class supports the following methods:
* 	1. Getters and setters for all members.
* 	2. public String getHtmlDescription() - produces a string with a description of the stock.
* 
* @param Stock
* @returns Stock
*/

@SuppressWarnings("unused")
public class Stock implements StockInterface{
	
	private ALGO_RECOMMENDATION recommendation;
	private Date outputDate;
	private Calendar cal;
	private String symbol;
	private float ask;
	private float bid;
	private int stockQuantity = 0;
	
	public Stock() {
		this.symbol = new String();
		this.outputDate = new Date(); 
		this.cal = Calendar.getInstance();
		this.recommendation = ALGO_RECOMMENDATION.HOLD;
	}
	
	public Stock(String newSymbol, float newAsk, float newBid) {
		this.symbol = newSymbol;
		this.ask = newAsk;
		this.bid = newBid;
		this.outputDate = new Date(); 
		this.cal = Calendar.getInstance();
	}
	
	public Stock(Stock stock) {
		this(stock.getSymbol(), stock.getAsk(),stock.getBid());
		this.stockQuantity = stock.getStockQuantity();
		this.outputDate = stock.getDate();
		this.cal = Calendar.getInstance();
		this.cal.setTime(this.outputDate);
		this.recommendation = stock.getRecommendation();
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public float getAsk() {
		return ask;
	}
	
	public void setAsk(float ask) {
		this.ask = ask;
	}
	
	public float getBid() {
		return bid;
	}
	
	public void setBid(float bid) {
		this.bid = bid;
	}
	
	public Date getDate() {
		return outputDate;
	}
	
	public void setDate(Date inputDate) {		
		Date now = new Date();
		this.outputDate = inputDate;		
		this.cal.setTime(outputDate);
		now = this.cal.getTime();
		this.outputDate = now;
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}

	public void updateStockQuantity(int stockQuantity) {
		this.stockQuantity += stockQuantity;
	}
	
	public ALGO_RECOMMENDATION getRecommendation() {
		return recommendation;
	}
	
	public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
		this.recommendation = recommendation;
	}
	
	public String getHtmlDescription()
	{
		SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd-yyyy"); 
		String stockDetails = new String("<b>Stock symbol</b>: " + getSymbol() + ", <b>ask</b>: " + getAsk() + ", <b>bid</b>: " + getBid() + ", <b>quantity</b>: " + getStockQuantity() + ", <b>date</b>: " + formatDate.format(getDate()));
		return stockDetails;
	}
}
