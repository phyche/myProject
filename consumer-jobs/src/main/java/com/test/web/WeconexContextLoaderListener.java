package com.test.web;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * ContextLoaderListener
 * @author TWY.TOM
 *
 */
public class WeconexContextLoaderListener extends ContextLoaderListener {
	
	static final Logger logger = Logger.getLogger(WeconexContextLoaderListener.class);

	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) {
		// spring context
		WebApplicationContext context = super.initWebApplicationContext(servletContext);

		return context;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}


}
