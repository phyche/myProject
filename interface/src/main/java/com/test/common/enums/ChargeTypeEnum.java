package com.test.common.enums;

public enum ChargeTypeEnum {

	RMB("1", "", "人民币"),
	TICKET_TRAFFIC("2", "", "充值券"),
	TICKET_PREPAID("3", "", "乘车券"),

	;
	private ChargeTypeEnum(String code, String flag, String description) {
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
	
	public static ChargeTypeEnum getEnumByCode(String code){
		for(ChargeTypeEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
