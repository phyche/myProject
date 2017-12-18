package com.test.entity;
/**
 * 带结果回应对象
 * @author twy
 *
 */
public class ResponeResultVO extends ResponeVO {
	/**
	 * 结果
	 */
	private Object result;
	
	public ResponeResultVO(Object result) {
		super();
		this.result = result;
	}
	
	public ResponeResultVO(int responseCode, String responseDesc, Object result) {
		super(responseCode, responseDesc);
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
