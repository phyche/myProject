/**   
 * @Title: PropertiesUtil.java 
 * @Package com.spider.core 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2015年6月5日 下午1:54:09 
 * @version V1.0   
 */
package com.myproject.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import com.google.common.collect.Maps;

/**
 * @ClassName: PropertiesUtil
 * @Description: properties 文件读取
 * @author cssuger@163.com
 * @date 2015年6月5日 下午1:54:09
 * 
 */
public class PropertiesUtil {

	private String resource;

	private Properties properties = new Properties();

	private  Map<String, String> readMap = Maps.newHashMap();

	/**
	 * <p> Title:</p>
	 * <p>Description:</p>
	 */
	public PropertiesUtil(String resource) {
		this.resource = resource;
		loadProperties();
		toMap();
	}

	private void loadProperties() {

		InputStream inputStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(resource);
		if (null == inputStream) {
			throw new NullPointerException("inputStream is not null");
		}
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(inputStream);
		}

	}

	private void close(InputStream inputStream) {
		try {
			if (null != inputStream) {
				inputStream.close();
			}
		} catch (IOException ex) {

		}
	}

	

	public Properties getProperties() {
		return properties;
	}

	public String getvalue(String key) {
		return properties.getProperty(key);
	}
	
	

	public Map<String, String> getReadMap() {
		return readMap;
	}

	private void setReadMap(Map<String, String> readMap) {
		this.readMap = readMap;
	}

	private void toMap(){
		
		Set<Entry<Object, Object>> set = properties.entrySet();
		Iterator<Entry<Object, Object>> iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<Object, Object> entry =  iterator.next();
		    if(entry.getKey() != null){
		    	String value = entry.getValue() == null ? "" :(String)entry.getValue();
		    	readMap.put((String)entry.getKey(), value);
		    }
		}
	}
	
	
//	public static void main(String[] args) {
//		System.out.println(new PropertiesUtil(Constant.JDBC_PROPERTIES_PATH).getReadMap());
//	}
}
