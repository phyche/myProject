/**
 * 
 */
package com.myproject.util;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

import com.myproject.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Administrator
 *
 */
public class ResponseUtil {

	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
	
	
	public static void printObjectToJson(HttpServletResponse response,Object object){
		PrintWriter write = null;
		response.setHeader("Content-type", "text/html;charset=UTF-8"); 
		try {
			write = response.getWriter();
			if(logger.isDebugEnabled()){
				logger.debug("json:"+ JsonUtils.toJson(object));
			}
			
			//System.out.println("-----------"+JsonUtil.objectToJson(object));
			write.println(JsonUtils.toJson(object));
		} catch (IOException e) {
			logger.error("",e.getCause());
		}finally{
			closeResource(write);
		}
	}
	
	public static void printStringToJson(HttpServletResponse response,String object){
		PrintWriter write = null;
		try {
			write = response.getWriter();
			//System.out.println("-----------"+JsonUtil.objectToJson(object));
			write.println(object);
		} catch (IOException e) {
			logger.error("",e.getCause());
		}finally{
			closeResource(write);
		}
	}
	
	public static void printString(HttpServletResponse response,String string){
		PrintWriter write = null;
		StringBuffer jsonsb = new StringBuffer();
		jsonsb.append("{").append("\"key\"").append(":").append("\"").append(string).append("\"").append("}");
		try {
			write = response.getWriter();
			write.println(JsonUtils.toJson(jsonsb.toString()));
		} catch (IOException e) {
			logger.error("",e.getCause());
		}finally{
			closeResource(write);
		}
	}
	
	
	private static void  closeResource(PrintWriter write){
		if(null != write){
			write.flush();
			write.close();
		}
	}
}
