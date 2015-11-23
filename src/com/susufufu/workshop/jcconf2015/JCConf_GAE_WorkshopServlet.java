package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class JCConf_GAE_WorkshopServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
