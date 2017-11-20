package com.test.common.enums;

public enum ProductPrefixEnum {

	TICKET_TRAFFIC_PRE("TT_", "", "乘车券前缀TT_"),
	TICKET_PREPAID_PRE("TP_", "", "充值券前缀TP_"),
	CARD_TRAFFIC_PRE("CT_", "", "公交卡前缀CT_"),


	;
	private ProductPrefixEnum(String code, String flag, String description) {
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
	
	public static ProductPrefixEnum getEnumByCode(String code){
		for(ProductPrefixEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
