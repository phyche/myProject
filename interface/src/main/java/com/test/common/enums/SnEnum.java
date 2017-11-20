package com.test.common.enums;

public enum SnEnum {
	
	SEQ_MEMBER_ORDER("SEQ_TT_MEMBER_ORDER", "YT01", "会员订单表"), 
	IMG_NO_EXIST("IMG_NO_EXIST", "IMG01", "图片不存在"),
	SEQ_NJCC_PAY_SYSTEM_CODE("SEQ_NJCC_PAY_SYSTEM_CODE","NJCC12","智汇app支付平台交易流水号"),
	SEQ_NJCC_QUICK_PAY_CODE("SEQ_NJCC_QUICK_PAY_CODE","NJCCQP12","快捷流水号")
	;
	private SnEnum(String code, String flag, String description) {
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
	
	public static SnEnum getEnumByCode(String code){
		for(SnEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
