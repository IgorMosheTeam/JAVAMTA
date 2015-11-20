package com.myorg.javacourse;

import java.util.*;
import java.text.*;
import java.lang.Object;

@SuppressWarnings("unused")
public class Stock {
	
	private String symbol;
	private float ask;
	private float bid;
	private String inputDate;
	private Date outputDate;
	
	public Stock() {
		inputDate = null;
		outputDate = new Date(); 
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

	public void setDate(String inputDate) {
		SimpleDateFormat formatedDate = new SimpleDateFormat("MM/dd/yyyy");
		try {
		    	this.outputDate = formatedDate.parse(inputDate);
		    } catch (ParseException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
	}
	
	public String getHtmlDescription()
	{
		String stockDetails = new String("<b>Stock symbol</b>: " + getSymbol() + ", <b>ask</b>: " + getAsk() + ", <b>bid</b>: " + getBid() + ", <b>date</b>: " + getDate());

		return stockDetails;
	}
}
