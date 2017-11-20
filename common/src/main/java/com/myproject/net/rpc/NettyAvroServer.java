/**   
* @Title: NettyAvroServer.java 
* @Package com.cyber.rpc 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月5日 下午9:17:41 
* @version V1.0   
*/
package com.myproject.net.rpc;

import java.net.InetSocketAddress;
import java.util.Map;

import com.myproject.util.PropertiesUtil;
import com.myproject.net.Server;
import com.myproject.net.protocol.ProtocolUtil;
import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.generic.GenericResponder;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/** 
 * @ClassName: NettyAvroServer 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年6月5日 下午9:17:41 
 *  
 */
public class NettyAvroServer extends GenericResponder implements Server {

private int port;
	
	private String host ;
	
	
	private Map<String,String> map = new PropertiesUtil("conf/resource.properties").getReadMap();
	
	public NettyAvroServer(Protocol local) {
		super(local);
		host = map.get("avro_host");
		port = Integer.parseInt(map.get("avro_port"));
	}

	@Override
	public Object respond(Message arg0, Object arg1) throws Exception {
		if(arg1 instanceof GenericRecord){
			GenericRecord record = (GenericRecord)arg1;
			GenericRecord resmg =  (GenericRecord)record.get("logentity");
			//System.out.println(resmg.get("body"));
			resmg.put("body", "消息已经接受到");
			return resmg;
		}
		return null;
	}
	
	public void start(){
		try {
			NettyServer server = new NettyServer(this, new InetSocketAddress(host,port));
			//HttpServer server = new HttpServer(this, port);
			server.start();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            configurator.doConfigure("E:/workspace/logmanager/src/main/java/conf/logback.xml");
       } catch (JoranException e) {
            e.printStackTrace();
        }
		new AvroHttpServer(ProtocolUtil.parserSchema()).start();
	}

}
