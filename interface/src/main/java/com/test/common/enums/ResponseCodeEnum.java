package com.test.common.enums;

public enum ResponseCodeEnum {
	RESPONSE_SUCCESS("000000", "", "支付平台返回成功"),
	RESPONSE_ERROR("0001", "", "自定义异常"),
	RESPONSE_ERROR_VERIFY("0002", "", "支付返回延签失败"),
	RESPONSE_ERROR_PWD_NOT_MATCH("41004029", "", "支付密码错误"),
	RESPONSE_ACCOUNT_REPEAT("0428", "", "账户重复code！"),
	RESPONSE_USERTRANSFER_ERROR_PWD_NOT_MATCH("1006", "", "转值支付密码错误code！"),
	RESPONSE_KEY_CODE("responseCode", "", "支付平台返回编码key"),
	RESPONSE_KEY_MSG("responseMsg", "", "支付平台返回描述key"),
	RESPONSE_KEY_DESC("responseDesc", "", "返回对外描述key"),
	/*交易类型：1001-支付，1002-退款，1004-充值，100601-转账到对方，100602-收取对方转账*/
	ORDER_ORDERDETAILINFO_TRADETYPE_PAY("1001", "", "查订单支付"),
	RESPONSE_PROCESSING("030204", "", "请求支付平台成功，支付订单处理中"),
	//订单状态10000-10004在用
	RESPONSE_ORDER_NOT_FOUND("10000", "", "订单不存在"),
	RESPONSE_ORDER_COMPLETE("10001", "", "订单已处理成功不可重复支付"),
	RESPONSE_ORDER_CLOSED("10002", "", "订单已关闭不可支付"),
	RESPONSE_ORDER_PROCESSING("10003", "", "订单处理中"),
	RESPONSE_ORDER_FAILED("10004", "", "该订单支付失败"),
	RESPONSE_ORDER_SUCCESS("10005", "", "该订单支付成功"),
	RESPONSE_ORDER_ERROR("10006", "", "该支付订单状态异常，请联系管理员"),
	REQUEST_PARAMS_ERROR("10007", "", "请求参数错误"),
	RESPONSE_DATA_ERROR("10008", "", "数据异常，请联系管理员"),
	RESPONSE_SERVICE_BUSY_ERROR("10009", "", "服务器繁忙"),
	REQUEST_PARAMS_ILLEGAL("10010", "", "请求参数不合法"),
	RESPONSE_TSM_CALLBACK_FAILED("10011", "", "TSM系统回调Apdu失败"),
	RESPONSE_TSM_FAILED("10012", "", "调用TSM系统失败"),
	RESPONSE_CARD_FAILED("10013", "", "充值卡号与预约卡号不一致"),
	TSM_APDU_SUCCESS("8888", "", "回调apdu成功"),
	TSM_ORDERID_SUCC("9002", "", "该订单已经充值完成或者开卡成功"),

	;
	private ResponseCodeEnum(String code, String flag, String description) {
		this.code = code;
		this.flag = flag;
		this.description = description;
	}

	/**
	 * seqences id
	 */
	private String code;

	/**
	 * 起始标志
	 */
	private String flag;

	/**
	 * 描述
	 */
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static ResponseCodeEnum getEnumByCode(String code){
		for(ResponseCodeEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
