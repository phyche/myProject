/**   
 * @Title: SequenceGenerator.java 
 * @Package com.boco.traffic.util.annotation 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2015年12月18日 上午9:23:28 
 * @version V1.0   
 */
package com.myproject.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: SequenceGenerator 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2015年12月18日 上午9:23:28 
 *  
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SequenceGenerator {

//	    /** 数据表名称注解，默认值为类名称
//	     * @return
//	     */
//	    public String name() default "";
	    
	    /**
	     * 
	    * @Title: sequenceName 
	    * @Description: Sequence名称
	    * @param @return    设定文件 
	    * @return String    返回类型 
	    * @throws
	     */
	    public String sequenceName() ;
	
}
