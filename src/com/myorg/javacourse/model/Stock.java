package com.myorg.javacourse.model;

import java.util.*;
import java.text.*;
import java.lang.Object;

import org.algo.model.StockInterface;
import com.myorg.javacourse.model.Portfolio.*;

/**
* This class built to represent a stock.
* It contains the stock's symbol(String), ask(float), bid(float), date(Date) and quantity(int).
* The class has two c'tors:
* 	1. public Stock() - creates a new stock object.
* 	2. public Stock(Stock stock) - creates a copy of a stock object.
* The class supports the following methods:
* 	1. Getters and setters for all members.
* 	2. public String getFormattedDate(Date outputDate) - provides formated date to output.
* 	3. public String getHtmlDescription() - produces a string with a description of the stock.
* 
* @param Stock
* @returns Stock
*/

@SuppressWarnings("unused")
public class Stock implements StockInterface{
	private ALGO_RECOMMENDATION recommend;
	private Date outputDate;
	private Calendar cal;
	private String symbol;
	private float ask;
	private float bid;
	private int stockQuantity = 0;
	
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
		this.cal.setTime(outputDate);
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
	
	/**
	 * @param outputDate
	 * @returns formated date from the cal object.
	 */
	public String getFormattedDate(Date outputDate) {
		SimpleDateFormat formatedCal = new SimpleDateFormat("MM/dd/yyyy");
		return formatedCal.format(cal.getTime());
	}

	public void setDate(String inputDate) {		
		SimpleDateFormat formatedDate = new SimpleDateFormat("MM/dd/yyyy");
		try {
		    	this.outputDate = formatedDate.parse(inputDate);
		    } catch (ParseException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		cal.setTime(outputDate);
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}

	public void updateStockQuantity(int stockQuantity) {
		this.stockQuantity += stockQuantity;
	}
	
	public String getHtmlDescription()
	{
		String stockDetails = new String("<b>Stock symbol</b>: " + getSymbol() + ", <b>ask</b>: " + getAsk() + ", <b>bid</b>: " + getBid() + ", <b>quantity</b>: " + getStockQuantity() + ", <b>date</b>: " + getFormattedDate(outputDate));
		return stockDetails;
	}
}
