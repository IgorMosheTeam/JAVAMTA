package com.myorg.javacourse.stock.servlet;

import java.io.IOException;
import javax.servlet.http.*;
import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.service.PortfolioManager;

@SuppressWarnings("serial")
public class PortfolioServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		PortfolioManager portfolioManager = new PortfolioManager();
		Portfolio portfolio = (Portfolio) portfolioManager.getPortfolio();
		resp.getWriter().println(portfolio.getHtmlString());
	}
}