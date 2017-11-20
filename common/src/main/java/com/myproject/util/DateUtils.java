package com.myproject.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	final static String DEFAULT_FORMAT = "yyyyMMdd";
	final static String fmtStr="yyyy-MM-dd HH:mm:ss";
	
	final static SimpleDateFormat sdfStr = new SimpleDateFormat("yyyy-MM-dd");
	final static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String praseDate(Date date, String pattern) {
		if (StringUtil.isEmpty(pattern)) {
			pattern = DEFAULT_FORMAT;
		}
		if(date==null)return "";
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static Date toDate(String dateString, String pattern) throws Exception {
		if (StringUtil.isEmpty(pattern)) {
			pattern = DEFAULT_FORMAT;
		}
		DateFormat df = new SimpleDateFormat(pattern);
		Date date;
		date = df.parse(dateString);
		return date;
	}

	public static Date makeUpTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		return cal.getTime();
	}

	/**
	 * @param startDate
	 *            开始日期时间
	 * @param endDate
	 *            结束日期时间
	 * @return 获取日期天数
	 */
	public static long getDiffDays(Date startDate, Date endDate) {
		try {
			long diff = endDate.getTime() - startDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			return days;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public static Date getQueryDateBefore(Date date) throws ParseException{
		if(null == date) return null;
		String str = sdfStr.format(date);
		String strBefore = str + " 00:00:00";
		return sdfDate.parse(strBefore);
	}
	
	public static Date getQueryDateAfter(Date date) throws ParseException{
		if(null == date) return null;
		String str = sdfStr.format(date);
		String strAfter = str + " 23:59:59";
		return sdfDate.parse(strAfter);
	}
	
	public static String getNow(){
		SimpleDateFormat sdf=new SimpleDateFormat(fmtStr);
		return sdf.format(new Date());
	}
	public static String subStrFormatTime(String value){
		if (value == null || value == "") {
			return "";
		}
		if(value.length()==14){
			//20160618163611
			return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(6,8)+' '+value.substring(8,10)+':'
					+value.substring(10,12)+':'+value.substring(12);
		}else if(value.length()==8){
			return value.substring(0,4)+'-'+value.substring(4,6)+'-'+value.substring(4,8);
		}
		return value;
	}

	public static Date stringToDate(String date) {

		try {
			return sdfDate.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取当前年月
	 * @return
     */
	public static String getYearAndMonth() {
		String yearAndMonth;
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		yearAndMonth = String.valueOf(year)+String.valueOf(month<10 ? "0" + month : month);
		return yearAndMonth;

	}
	public static void main(String[] args) {
		//System.out.println(DateUtil.makeUpTime());
		System.out.println(getYearAndMonth());
	}
}
