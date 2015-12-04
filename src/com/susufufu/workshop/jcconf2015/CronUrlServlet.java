package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CronUrlServlet extends HttpServlet {
	private Logger log = Logger.getLogger("CronUrlServlet");
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		log.warning("This is doGet....");
		resp.setContentType("text/plain");
		resp.getWriter().println("This is doGet...");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		log.warning("This is doPost....");
		resp.setContentType("text/plain");
		resp.getWriter().println("This is doPost...");
	}
}
