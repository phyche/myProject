package com.test.utils;

import com.test.handler.ApiHandler;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ConcurrentHashMap;

public class NcvasApplicationContextUtils {

	private static ApplicationContext context;
	private static ConcurrentHashMap<String, ApiHandler> apiHandlerClassMaps = new ConcurrentHashMap<String, ApiHandler>();

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		NcvasApplicationContextUtils.context = context;
	}

	public static ConcurrentHashMap<String, ApiHandler> getApiHandlerClassMaps() {
		return apiHandlerClassMaps;
	}

	public static void setApiHandlerClassMaps(
			ConcurrentHashMap<String, ApiHandler> apiHandlerClassMaps) {
		NcvasApplicationContextUtils.apiHandlerClassMaps = apiHandlerClassMaps;
	}
	
}
