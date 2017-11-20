package com.just.mq.test; /**
* @Title: UserVipTest.java 
* @Package com.cyber.vip.test 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月21日 上午11:13:49 
* @version V1.0   
*/

import com.test.common.mq.queue.send.QueueSender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserVipTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年6月21日 上午11:13:49 
 *  
 */
public class CommonTest {
    ApplicationContext ctx = null;
    QueueSender queueSender=null;
    @Before
    public void Init(){
        List<String> resourceList = new LinkedList<>();
        resourceList.add("spring/applicationContext.xml");
        try {
            List<String> packKey = new LinkedList<>();
            packKey.add("dev");
            packKey.add("justgotest");
            packKey.add("jdcloudtest");

            packKey.forEach(key->{
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();      //将首先通过
                try {
                    Resource[] tmpRes = resolver.getResources("classpath*:"+key+"/*.xml");
                    if(tmpRes.length>0){
                        for(Resource resTmp : tmpRes){
                            resourceList.add(key+"/"+resTmp.getFilename());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(resourceList.toArray(new String[0]));
            ctx = context;
        }catch (Exception e){
            e.printStackTrace();
        }
        queueSender =(QueueSender) ctx.getBean("queueSender");
    }

    @Test
    public void testUpdateChannelNoMq(){
        String orderId = "20171012022202699131507789322699";
        Map<String, Object> param = new HashMap<>();
        param.put("orderId", orderId);
        param.put("channelOrderNo", orderId);
        queueSender.sendMap("jgUpdateChannelNoToPayOrderDestination", param);
    }



}
