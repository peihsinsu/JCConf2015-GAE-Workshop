package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

/**
 * curl -X GET https://jcconf2015-dot-m-plaza.appspot.com/DatastoreExample
 * @author peihsinsu
 *
 */
@SuppressWarnings("serial")
public class Lab1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException{
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		String username = req.getParameter("username");
		String sex = req.getParameter("sex");
		int age = Integer.parseInt(req.getParameter("age"));
		String current_job =  req.getParameter("current_job");
		//String email = req.getParameter("email");
		
		Entity employee = null;
		Key empKey = null;
		
		//Begin transaction
		Transaction txn = datastore.beginTransaction();
		
		//Add data
		try {
			employee = new Entity("Member", username);
			employee.setProperty("sex", sex);
			employee.setProperty("age", age);
			employee.setProperty("current_job", current_job);
			//employee.setProperty("email", email);
			
			empKey = datastore.put(employee);
			txn.commit();
			out.println("1. write to datastore done..." + empKey.getId());
		} catch (Exception e) {
			e.printStackTrace();
			if(txn.isActive())
				txn.rollback();
		}
		
		//Get data from specified key
		Key k = KeyFactory.createKey("Member", username);
		try {
			Entity user = datastore.get(k);
			Map data = user.getProperties();
			out.println("2. get datastore data..." + data.toString() );
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
