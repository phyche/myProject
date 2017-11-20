package com.myproject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpConnectionUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpConnectionUtil.class);

	public static String postMethod(String url,String postString,int connectTimeout,int readTimeout){
		String result = null;
		HttpURLConnection conn = null;
		try {
			URL postUrl = new URL(url);
			conn = (HttpURLConnection)postUrl.openConnection();
			conn.setRequestMethod("POST");				
			conn.setDoOutput(true);   
			conn.setDoInput(true);   
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			logger.info("content：开始传递==================>>>地址{}，参数{}",url,postString);
			OutputStream os = conn.getOutputStream();
			os.write(postString.getBytes("utf-8")); // 往远程URL传递参数			          
            os.flush();
            os.close();
            
            int code = conn.getResponseCode();
			logger.info("返回状态码："+code+", 返回长度："+conn.getContentLength());
            conn.getHeaderFields();
            conn.getContentLength();
            if(code==200){//返回成功
            	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            	String line;
            	StringBuffer buffer = new StringBuffer();
            	while((line = reader.readLine()) != null) {
            		buffer.append(line);
            	}
            	result = buffer.toString();
            }else{
				logger.info("返回失败："+code);
            }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
