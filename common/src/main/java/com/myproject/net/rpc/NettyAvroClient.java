/**   
 * @Title: NettyAvroClient.java 
 * @Package com.cyber.rpc 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2016年6月5日 下午9:19:39 
 * @version V1.0   
 */
package com.myproject.net.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.myproject.net.Client;
import com.myproject.util.PropertiesUtil;
import com.myproject.net.protocol.ProtocolUtil;
import org.apache.avro.Protocol;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.Callback;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: NettyAvroClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cssuger@163.com
 * @date 2016年6月5日 下午9:19:39
 * 
 */
public class NettyAvroClient implements Client {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private Protocol protocol = null;
	private Map<String, String> map = new PropertiesUtil(
			"conf/resource.properties").getReadMap();
	private String host = null;
	private int port = 0;

	public NettyAvroClient(Protocol protocol) {
		this.protocol = protocol;
		this.host = map.get("avro_host");
		this.port = Integer.parseInt(map.get("avro_port"));

	}

	public void sendMessage(String inputstr) {

	}

	private void colse(Transceiver t) {
		try {
			if (t.isConnected()) {
				t.close();
			}
		} catch (Exception ex) {
		}

	}

	public long run(String inputstr) {
		long res = 0;
		try {
			sendMessage(inputstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) throws Exception {
		String resmg = "根据新华社稿件，6月3日，习近平主席在人民大会堂同柬埔寨国王西哈莫尼举行会谈。双方一致同意，巩固睦邻友好，深化互利合作，推动中柬全面战略合作伙伴关系不断向前发展，给两国人民带来更多福祉。习近平指出：“中柬是情同手足的好邻居，也是肝胆相照的好朋友。”这么高的定位，是从哪里来的？不妨从习主席的话语中找找答案：西哈努克太皇同中国老一辈领导人共同缔造并精心培育的中柬友谊日益深入人心，成为两国人民的宝贵财富。国王陛下继承太皇陛下对华友好事业，长期致力于弘扬中柬传统友谊，我们对此予以高度评价。中方珍视同柬埔寨王室的特殊友谊，高度重视发展对柬埔寨关系，支持柬方走符合本国国情的发展道路。习主席提到的“特殊友谊”，也可以从历史中找到源头。1955年4月，周恩来总理与西哈努克在万隆会议上结识，从此掀开中柬友好关系的历史性篇章。";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			System.out.println(i+"-----------------------------------------------------");
			NettyAvroClient client = new NettyAvroClient(
					ProtocolUtil.parserSchema());
			client.sendLogger(resmg);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);

		// new Client(Utils.getProtocol(), "127.0.0.1", 10056).run();
	}

	@Override
	public void sendLogger(String logger) {
		GenericRecord requestData = new GenericData.Record(protocol.getType("LogEntity"));
		requestData.put("body", logger);
		GenericRecord request = new GenericData.Record(protocol.getMessages().get("sendLogger").getRequest());
		request.put("logentity", requestData);
		// 客户端连接服务器
		Transceiver t = null;
		try {
			t = this.connection();
			// t = new HttpTransceiver(new URL("http://" + host + ":" + port));
			GenericRequestor requestor = new GenericRequestor(protocol, t);
			requestor.request("sendLogger", request, new Callback<Record>() {

				@Override
				public void handleResult(Record result) {
					// Record response = (GenericData.Record)objet;
					// logger.info("收到消息为:{}",result.get("body"));

				}

				@Override
				public void handleError(Throwable error) {

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colse(t);
		}

	}

	@Override
	public Transceiver connection() {
		Transceiver t = null;
		try {
			t = new NettyTransceiver(
					new InetSocketAddress(this.host, this.port), 2000L);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;

	}
}
