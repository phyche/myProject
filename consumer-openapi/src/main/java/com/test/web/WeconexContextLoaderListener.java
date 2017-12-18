package com.test.web;

import com.test.handler.ApiHandler;
import com.test.utils.NcvasApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * ContextLoaderListener
 * @author TWY.TOM
 *
 */
public class WeconexContextLoaderListener extends ContextLoaderListener {
	
	static final Logger logger = LoggerFactory.getLogger(WeconexContextLoaderListener.class);

	@Override
	public WebApplicationContext initWebApplicationContext(
			ServletContext servletContext) {
		// spring context
		WebApplicationContext context = super.initWebApplicationContext(servletContext);
		NcvasApplicationContextUtils.setContext(context);
		this.initApiHandlerConfigMaps(context);
		// 支付结果定时线程
//		PayStatusQueryThread.getInstatnce().start();

/*		Map poolConfigMap = new HashMap();
		Configuration configuration = (Configuration)context.getBean("configuration");
		poolConfigMap.put("serverIp", configuration.getSms_thrift_IP());
		poolConfigMap.put("serverPort", configuration.getSms_thrift_Port());
		poolConfigMap.put("defaultPoolSize", configuration.getDefaultPoolSize());
		poolConfigMap.put("maxPoolSize", configuration.getMaxPoolSize());
		poolConfigMap.put("minPoolSize", configuration.getMinPoolSize());
		poolConfigMap.put("maxWait", configuration.getMaxWait());
		poolConfigMap.put("acquireIncrement", configuration.getAcquireIncrement());
		poolConfigMap.put("connectTimeOut", configuration.getConnectTimeOut());
		poolConfigMap.put("connectionErrorRetryTimes", configuration.getConnectionErrorRetryTimes());
		//初始化短信平台thrift连接池
		SysConfig.BCARD_THRIFTPOOL = new BCardThriftConnectPool(poolConfigMap);*/


		return context;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
	}

	/**
	 * 初始化接口类
	 * @param applicationContext
	 * @throws BeansException
	 */
	private void initApiHandlerConfigMaps(ApplicationContext applicationContext)
			throws BeansException {
		HashMap<String, ApiHandler> apiHandlerClassMaps = new HashMap<String, ApiHandler>();
		@SuppressWarnings("unchecked")
		HashMap<String, String> hashMap = (HashMap<String, String>) applicationContext.getBean("apiHandlerClassMaps");
		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
			try {
				Object apiHandler = applicationContext.getBean(Class.forName(entry.getValue()));
				if (apiHandler instanceof ApiHandler) {
					apiHandlerClassMaps.put(entry.getKey(), (ApiHandler) apiHandler);
					logger.debug("ApiHandler[code=" + entry.getKey() + ", class=" + apiHandler.getClass().getName() + "]");
				}
			} catch (Exception e) {
				logger.error("setApplicationContext", e);
			}
		}
		NcvasApplicationContextUtils.getApiHandlerClassMaps().putAll(apiHandlerClassMaps);
	}
}
