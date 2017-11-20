package com.test.common.enums;

public enum ReqBizTypeEnum {

	REQ_BIZ_TYPE_JUST_GO("1", "", "来自出门无忧"),

	;
	private ReqBizTypeEnum(String code, String flag, String description) {
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
	
	public static ReqBizTypeEnum getEnumByCode(String code){
		for(ReqBizTypeEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
