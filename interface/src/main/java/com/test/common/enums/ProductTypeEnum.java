package com.test.common.enums;

public enum ProductTypeEnum {

	TICKET_PREPAID("1", "", "乘车券"),
	TICKET_TRAFFIC("2", "", "充值券"),
	CARD_TICKET("CT_001", "", "公交卡充值"),
	;
	private ProductTypeEnum(String code, String flag, String description) {
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
	
	public static ProductTypeEnum getEnumByCode(String code){
		for(ProductTypeEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
