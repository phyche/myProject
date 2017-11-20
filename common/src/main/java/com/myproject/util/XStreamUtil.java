package com.myproject.util;

import com.thoughtworks.xstream.XStream;

/** 
  *@ClassName: XStreamUtil 
  *@Description: TODO 
  *@author: yanhewei@boco.com.cn 
  *@date: 2016年3月24日 下午2:34:25   
  */

public class XStreamUtil {

	
	public static  String objectToStringXml(Object object){
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);  
	    String xml = xstream.toXML(object);
	    return xml;
	}
	
	public static  Object convertXmlToObject(String strxml,Class clazz){
		XStream xstream = new XStream();
		xstream.setClassLoader(clazz.getClassLoader());
		return xstream.fromXML(strxml);
	     
	}
}

	