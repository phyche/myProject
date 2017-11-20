/**   
* @Title: UserBehaviorJMSQueueAppender.java 
* @Package com.cyber.vo.log 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年5月25日 下午6:35:49 
* @version V1.0   
*/
package com.myproject.log;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import com.myproject.util.Constants;
import com.myproject.util.PropertiesUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import ch.qos.logback.classic.net.LoggingEventPreSerializationTransformer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.net.JMSAppenderBase;
import ch.qos.logback.core.spi.PreSerializationTransformer;

/**
 * @ClassName: UserBehaviorJMSQueueAppender 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年5月25日 下午6:35:49 
 *  
 */
public class UserBehaviorJMSQueueAppender extends JMSAppenderBase<ILoggingEvent>
{
  static int SUCCESSIVE_FAILURE_LIMIT = 3;
  String queueBindingName;
  String qcfBindingName;
  QueueConnection queueConnection;
  QueueSession queueSession;
  QueueSender queueSender;
  int successiveFailureCount = 0;

  private PreSerializationTransformer<ILoggingEvent> pst = new LoggingEventPreSerializationTransformer();

  public void setQueueConnectionFactoryBindingName(String qcfBindingName)
  {
    this.qcfBindingName = qcfBindingName;
  }

  public String getQueueConnectionFactoryBindingName()
  {
    return this.qcfBindingName;
  }

  public void setQueueBindingName(String queueBindingName)
  {
    this.queueBindingName = queueBindingName;
  }

  public String getQueueBindingName()
  {
    return this.queueBindingName;
  }

  public void start()
  {
    Map<String,String> map = new PropertiesUtil(Constants.JNDI_PATH).getReadMap();
    try
    {
      QueueConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory((String)map.get("providerUrl"));

      this.queueConnection = queueConnectionFactory.createQueueConnection();

      this.queueSession = this.queueConnection.createQueueSession(false, 1);

      Queue queue = this.queueSession.createQueue((String)map.get("queueName"));

      this.queueSender = this.queueSession.createSender(queue);
      this.queueConnection.start();
      if ((this.queueConnection != null) && (this.queueSession != null) && (this.queueSender != null))
      {
        super.start();
      }
    }
    catch (JMSException e)
    {
      e.fillInStackTrace();
    }
  }

  public synchronized void stop()
  {
    if (!this.started) {
      return;
    }

    this.started = false;
    try
    {
      if (this.queueSession != null) {
        this.queueSession.close();
      }
      if (this.queueConnection != null)
        this.queueConnection.close();
    }
    catch (Exception e) {
      addError("Error while closing JMSAppender [" + this.name + "].", e);
    }

    this.queueSender = null;
    this.queueSession = null;
    this.queueConnection = null;
  }

  public void append(ILoggingEvent event)
  {
    if (!isStarted()) {
      return;
    }
    try
    {
      TextMessage msg = this.queueSession.createTextMessage();

      msg.setText(event.getMessage());
      this.queueSender.send(msg);
      this.successiveFailureCount = 0;
    } catch (Exception e) {
      this.successiveFailureCount += 1;
      if (this.successiveFailureCount > SUCCESSIVE_FAILURE_LIMIT) {
        stop();
      }
      addError("Could not send message in JMSQueueAppender [" + this.name + "].", e);
    }
  }

  protected QueueConnection getQueueConnection()
  {
    return this.queueConnection;
  }

  protected QueueSession getQueueSession()
  {
    return this.queueSession;
  }

  protected QueueSender getQueueSender()
  {
    return this.queueSender;
  }
}
