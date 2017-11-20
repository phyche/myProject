/**
 * 
 */
package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * @author zyl
 * @date 2016年7月1日
 * 
 */
public class Run {
	public static void main(String[] args) {
		List<String> resourceList = new LinkedList<>();
		resourceList.add("spring/applicationContext.xml");
		try {
			List<String> packKey = new LinkedList<>();
			packKey.add("dev");
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
			context.start();
			System.out.println("================================载入的xml开始================================");
			resourceList.forEach(xml->System.out.println(xml));
			System.out.println("================================载入的xml结束================================");

			//			展示启动获取的配置信息，方便启动确认配置文件信息
			System.out.println("================================系统配置新建信息开始================================");
			Properties config = (Properties)context.getBean("configProperties");
			Enumeration configEnu=config.propertyNames();
			while(configEnu.hasMoreElements()){
				String key = (String)configEnu.nextElement();
				System.out.println(key+"----"+config.getProperty(key));
			}
			System.out.println("================================系统配置新建信息结束================================");
		}catch (Exception e){
			e.printStackTrace();
		}

		System.out.println("dubbo database started....");
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		while(true){
			try {
				TimeUnit.DAYS.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
