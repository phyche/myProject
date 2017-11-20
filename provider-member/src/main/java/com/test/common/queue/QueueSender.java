package com.test.common.queue;

import com.myproject.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zyl
 * @date 2016年5月19日
 * 
 */
@Component("queueSender")
public class QueueSender {

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;// 通过@Qualifier修饰符来注入对应的bean

	/**
	 * 发送一条消息到指定的队列（目标）
	 * 
	 * @param queueName
	 *            队列名称
	 * @param message
	 *            消息内容
	 */
	public void send(String queueName, final String message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}
	public void sendMap(String queueName,final Map data) {
		System.out.println("---------------生产者发送消息-----------------");
		System.out.println("---------------生产者发了一个消息：" + JsonUtils.toJson(data));
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mm = session.createMapMessage();
				Iterator<String> keys = data.keySet().iterator();
				while (keys.hasNext()){
					String key = keys.next();
					mm.setObject(key,data.get(key));
				}
				return mm;
			}
		});
	}
}
