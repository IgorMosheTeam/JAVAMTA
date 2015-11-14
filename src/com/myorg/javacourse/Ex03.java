package com.myorg.javacourse;

import java.io.IOException;
import javax.servlet.http.*;
import java.lang.Object;
import java.lang.String;

@SuppressWarnings("serial")
public class Ex03 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
				
		Calculator calculator = new Calculator();
		String resultStr = calculator.getResults();
		resp.getWriter().println(resultStr);
	}
}
