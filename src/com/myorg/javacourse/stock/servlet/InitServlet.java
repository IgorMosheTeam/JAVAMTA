package com.myorg.javacourse.stock.servlet;

import java.io.IOException;
import javax.servlet.http.*;
import org.algo.service.ServiceManager;
import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.service.PortfolioManager;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	public void init() {
		PortfolioManager pm = new PortfolioManager();
		ServiceManager.setPortfolioManager(pm);			
	}
}