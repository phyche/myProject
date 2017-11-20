package com.test.common.enums;

public enum ActiveStateEnum {

	IN_ACTIVATED("0", "", "未激活"),
	ACTIVATED("1", "", "已激活"),

	DISABLE("0", "", "不展示"),
	SHOW("1", "", "展示"),

	;
	private ActiveStateEnum(String code, String flag, String description) {
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
	
	public static ActiveStateEnum getEnumByCode(String code){
		for(ActiveStateEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
