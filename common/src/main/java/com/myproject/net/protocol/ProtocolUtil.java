/**   
 * @Title: ProtocolUtil.java 
 * @Package com.cyber.rpc 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2016年6月5日 下午5:44:18 
 * @version V1.0   
 */
package com.myproject.net.protocol;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.avro.Protocol;

/**
 * @ClassName: ProtocolUtil
 * @Description: avro 协议读取
 * @author cssuger@163.com
 * @date 2016年6月5日 下午5:44:18
 * 
 */
public class ProtocolUtil {

	public static Protocol getProtocol(File file) {
		try {
			return Protocol.parse(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	

	public static Protocol parserSchema() {
		Protocol protocol = null;
		try {
			InputStream inputStream = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("conf/log.avpr");
			// URL url =
			// AvroProtocol.class.getClassLoader().getResource("conf/log.avpr");
			protocol = Protocol.parse(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return protocol;
	}

	public static void main(String[] args) {

		System.out.println(ProtocolUtil.parserSchema());
	}
}
