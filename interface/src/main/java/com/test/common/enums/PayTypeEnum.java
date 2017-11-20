package com.test.common.enums;

public enum PayTypeEnum {

	BUS_CARD_RECHARGE("1", "", "公交卡充值"),
	PAY_FOR_QR_CODE("3", "", "二维码订单支付"),
	PAY_FOR_ACC_PWD("1", "", "账户支付密码支付"),

	;
	private PayTypeEnum(String code, String flag, String description) {
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
	
	public static PayTypeEnum getEnumByCode(String code){
		for(PayTypeEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
