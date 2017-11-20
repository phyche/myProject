/**   
* @Title: SequenceMapper.java 
* @Package com.cyber.interceptor.annotation 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月27日 下午10:13:02 
* @version V1.0   
*/
package com.myproject.interceptor.annotation;

/** 
 * @ClassName: SequenceMapper 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author cssuger@163.com 
 * @date 2016年6月27日 下午10:13:02 
 *  
 */
public class SequenceMapper implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919221115993313914L;
	
	private String sequenceName;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	@Override
	public String toString() {
		return "SequenceMapper [sequenceName=" + sequenceName + "]";
	}
	
	

}
