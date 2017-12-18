package com.test.entity;
/**
 * 回应对象
 * @author twy
 *
 */
public class ResponeVO {

	/**
	 * 名称：响应代码 描述： 响应代码
	 **/
	protected int responseCode;

	/**
	 * 名称：响应说明 描述： 响应说明
	 **/
	protected String responseDesc;
	
	public ResponeVO() {
		this(0, "成功");
	}
	
	public ResponeVO(int responseCode, String responseDesc) {
		super();
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public void createSignStr() {
	}

	@Override
	public String toString() {
		return "ResponeVO [responseCode=" + responseCode + ", responseDesc="
				+ responseDesc + "]";
	}
	
}
