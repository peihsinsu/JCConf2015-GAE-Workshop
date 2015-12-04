package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * curl -X GET https://jcconf2015-dot-m-plaza.appspot.com/DatastoreExample
 * @author peihsinsu
 *
 */
@SuppressWarnings("serial")
public class DatastoreExample extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity employee = null;
		Key empKey = null;
		
		//Begin transaction
		Transaction txn = datastore.beginTransaction();
		
		//Add data
		try {
			//employee = new Entity("Employee");
			employee = new Entity("Employee", "simonsu.mail@gmail.com");
			employee.setProperty("name", "simonsu");
			employee.setProperty("hireDate", new Date());
			
			empKey = datastore.put(employee);
			txn.commit();
			out.println("1. write to datastore done..." + empKey.getId());
		} catch (Exception e) {
			e.printStackTrace();
			if(txn.isActive())
				txn.rollback();
		}
		
		//Get data from specified key
		Key k = KeyFactory.createKey("Employee", "simonsu.mail@gmail.com");
		try {
			Entity user = datastore.get(k);
			Map data = user.getProperties();
			out.println("2. get datastore data..." + data.toString() );
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		//Query data from datastore with equal query
		Query query = new Query("Employee");
		Query.Filter nameFilter = 
				new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, "simonsu");
		query.setFilter(nameFilter); 
		
		//Sorting
		query.addSort("hireDate");
		
		PreparedQuery results = datastore.prepare(query);
		Iterator<Entity> iter = results.asIterator();
		while(iter.hasNext()) {
			Entity ent = iter.next();
			out.println("3. query and got:" + ent.getProperties());
		}
		
		//Memcache example
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		cache.put("simonsu.mail@gmail.com", employee);
		out.println("5. put to memcache done...");
		
		Entity fromMemcache = (Entity) cache.get("simonsu.mail@gmail.com");
		out.println("6. get from memcache: " + fromMemcache.getProperties());
		
		
	}
}
