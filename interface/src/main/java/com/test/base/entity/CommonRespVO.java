package com.test.base.entity;
/**
 * 抽象公共回应类
 * @author TWY.TOM
 *
 */
public class CommonRespVO {

	protected String responseCode;
	protected String responseDesc;
	
	protected String signType;
	protected String signMsg;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDesc() {
		return responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	/**
	 * 交易成功
	 * @return
	 */
	public boolean isSuccess() {
		return "0000".equals(responseCode);
	}
	/**
	 * 交易已受理，但尚未处理完成。一般用于异步处理的接口。
	 * @return
	 */
	public boolean isProcessing() {
		return "0001".equals(responseCode);
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	@Override
	public String toString() {
		return "responseCode=" + responseCode + ", responseDesc="
				+ responseDesc + ", signType=" + signType + ", signMsg="
				+ signMsg;
	}
	
}
