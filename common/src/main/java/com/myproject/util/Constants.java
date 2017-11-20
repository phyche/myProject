/**
 * 
 */
package com.myproject.util;

/**
 * @author zyl
 * @date 2016年5月25日
 * 
 */
public class Constants {

	public static final String SESSION_USER = "session_user";
	
	public static final boolean SUCESS_STATE = true;
	
	public static final boolean ERROR_STATE = false;
	
	public static final String ERROR_TIP = "系统未知错误";
	
	public static final String VISITOR = "guest";
	
	public static final String DATE_FORMATE = "yyyyMMddHHmmsssss";
	
	
	public static final String JNDI_PATH = "conf/jndi.properties";
	
	public static final String BANK_KEY = "CNBANK";
	
//	public static final String JDBC_PATH = "db.properties";
//	
//	public static final String XMP_PATH = "classpath:spring-other.xml";
	
	public static final String REDIS_PROPERTIES_PATH = "conf/redis.properties";
	
	public static final String LOGIN_SIGN = "LOGIN_SIGN";
	public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
	
	public static void main(String[] args) {
		System.out.println(new PropertiesUtil(Constants.JNDI_PATH).getReadMap());
	}
}
