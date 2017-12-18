package com.test.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedisManageInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(RedisManageInterceptor.class);

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		releaseRedis();
	}
	private void releaseRedis() throws Exception{
// 获取当前线程绑定的连接
		RedisConnection conn = RedisConnectionUtils.bindConnection(redisTemplate.getConnectionFactory(), true);
//返还给连接池
		conn.close();
		TransactionSynchronizationManager.unbindResource(redisTemplate.getConnectionFactory());
	}
}
