/**
 * File Name:    IPAddressUtil.java
 *
 * File Desc:    取客户IP地址帮助类

 *
 * Product AB:   NEWTRADE_1_0_0
 *
 * Product Name: NEWTRADE
 *
 * Module Name:  Web.util
 *
 * Module AB:    Web.util
 *
 * Author:       何立勇

 *
 * History:      2008-11-10 created by 何立勇

 */
package com.myproject.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 取客户IP地址帮助类
 * 
 * 
 * @author Liyong He
 * @version 1.0
 */
public class IPAddressUtil {
	/**
	 * 从HTTP请求中获取客户IP地址
	 * 
	 * @param request
	 *            http请求
	 * @return 客户IP地址
	 */
	public static String getIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
