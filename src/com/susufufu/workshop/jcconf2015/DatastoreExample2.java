







package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * curl -X GET https://jcconf2015-dot-m-plaza.appspot.com/DatastoreExample2
 * 
 * @author peihsinsu
 *
 */
@SuppressWarnings("serial")
public class DatastoreExample2 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter out = resp.getWriter();

		//Create an Entity with Ancestor
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key userKey = KeyFactory.createKey("User", "simonsu.mail@gmail.com");
		Entity job = new Entity("Jobs", "engineer001", userKey);
		//Entity job = new Entity("Jobs", userKey); //without id, record will increase
		job.setProperty("name", "engineer");
		job.setProperty("start", new Date());
		try {
			Key k = ds.put(job);
			out.println("1. save data done... saved id:" + k.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Get data with Ancestor
		Query q = new Query("Jobs");
		Key ancestor = KeyFactory.createKey("User", "simonsu.mail@gmail.com");
		q.setAncestor(ancestor);

		PreparedQuery results = ds.prepare(q);
		Iterator iter = results.asIterable().iterator();
		while (iter.hasNext()) {
			Entity ent = (Entity) iter.next();
			out.println("2. get data:" + ent);
		}

	}
}
