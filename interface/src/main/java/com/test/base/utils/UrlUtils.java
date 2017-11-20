package com.test.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {

	public static String decode(String value) {
		try {
			return URLDecoder.decode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		return "";
	}

	public static String decodeGBK(String value) {
		try {
			return URLDecoder.decode(value, "GBK");
		} catch (UnsupportedEncodingException e) {

		}
		return "";
	}
	
	public static String decodeGBK2(String value) {
		try {
			return new String(value.getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e) {

		}
		return "";
	}

	public static String encode(String value) {
		try {
			return URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		return "";
	}
}
