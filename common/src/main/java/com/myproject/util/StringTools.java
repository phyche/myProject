/**   
* @Title: StringTools.java 
* @Package com.cyber.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年5月24日 下午4:47:11 
* @version V1.0   
*/
package com.myproject.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/** 
 * @ClassName: StringTools 
 * @Description: 字符串常用处理方法
 * @author cssuger@163.com 
 * @date 2016年5月24日 下午4:47:11 
 *  
 */
public class StringTools {

	/**
	 * 
	 * @Title: mkString 
	 * @Description: 讲null 或者""转换为"" 
	 * @param @param str
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String mkString(final String str){
		return StringUtils.trimToEmpty(str);
	}
	
	/**
	 * 
	 * @Title: isBlank 
	 * @Description: 判断该字符串是否为""，如果为""返回true，否则返货false 
	 * @param @param str
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isBlank(final String str){
		return StringUtils.isBlank(mkString(str));
	}
	
	public static String dateNow(String formate){
		SimpleDateFormat dateformate = new SimpleDateFormat(formate);
		return dateformate.format(new Date());
	}
	
	/**
	 * 
	 * @Title: beanCopy 
	 * @Description: bean 拷贝
	 * @param @param dist 目标对象
	 * @param @param org    原始对象
	 * @return void    返回类型 
	 * @throws
	 */
	public static void beanCopy(Object dest,Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: beanToMap 
	 * @Description: 将bean转换为map集合 
	 * @param @param bean
	 * @param @return    设定文件 
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	public static Map<String,Object> beanToMap(Object bean){
		try {
			return PropertyUtils.describe(bean);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
