package com.test.common.enums;

public enum StatusEnum {

	INVALID("0", "", "无效"),
	ACTIVE("1", "", "有效"),

	FAIL("0", "", "失败"),
	SUCCESS("1", "", "成功"),
	PROCESSING("2", "", "处理中"),
	CLOSED("3", "", "订单关闭"),

	CLOSING("4", "", "订单关闭处理中"),
	CHANGING("5", "", "标记订单处于处理中状态, 查询订单接口处理中"),
	TO_BE_PAID("6", "", "待支付"),
	ARREARS("2", "", "欠费"),
    SUPPLEMENT_PAYMENT_SUCCESS("3", "", "补缴费成功"),

	;
	private StatusEnum(String code, String flag, String description) {
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
	
	public static StatusEnum getEnumByCode(String code){
		for(StatusEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
