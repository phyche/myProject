/**   
* @Title: Client.java 
* @Package com.cyber.rpc 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月5日 下午9:19:53 
* @version V1.0   
*/
package com.myproject.net;

import org.apache.avro.ipc.Transceiver;

/** 
 * @ClassName: Client 
 * @Description:  
 * @author cssuger@163.com 
 * @date 2016年6月5日 下午9:19:53 
 *  
 */
public interface Client {

	public Transceiver connection();
	
	/**
	 * 
	 * @Title: sendLogger 
	 * @Description: 发送日志
	 * @param @param logger    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public void sendLogger(String logger);
}
