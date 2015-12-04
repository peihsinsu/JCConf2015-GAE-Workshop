package com.susufufu.workshop.jcconf2015;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class QueueExample extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Queue queue = QueueFactory.getDefaultQueue();
		//QueueFactory.getQueue("Qname");
		//Queue pullQueue = QueueFactory.getQueue("ProcessQueue");

		TaskOptions pushTask = TaskOptions.Builder.withUrl("/queue")
				.param("name", "simonsu");

		TaskOptions pullTask = TaskOptions.Builder
				.withMethod(TaskOptions.Method.PULL)
				.payload("this is payload");

		queue.add(pushTask);
		queue.add(pullTask);

		resp.setContentType("text/plain");
		resp.getWriter().println("Create task done...");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		cache.setErrorHandler(
		    ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		System.out.println("GET reqeust..." + cache.get("id"));
		resp.setContentType("text/plain");
		resp.getWriter().println("Somebody call get...");
	}
}
