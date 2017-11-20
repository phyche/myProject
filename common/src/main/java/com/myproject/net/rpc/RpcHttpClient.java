/**   
* @Title: RpcHttpClient.java 
* @Package com.cyber.net.rpc 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月30日 下午3:40:22 
* @version V1.0   
*/
package com.myproject.net.rpc;

import com.myproject.net.protocol.ProtocolUtil;

/** 
 * @ClassName: RpcHttpClient 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年6月30日 下午3:40:22 
 *  
 */
public class RpcHttpClient {

	public static void sendLog(String logstr){
		try{
	    	new AvroRpcHttpClient(ProtocolUtil.parserSchema()).sendLogger(logstr);
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
		
	}
}
