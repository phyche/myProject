package com.test.base.enums;

public enum MatchPolicyEnum {

	//1 匹配时间 2 匹配文字 3 配置文字时间

	MATCH_POLICY_TIME("1", "", "匹配时间"),
	MATCH_POLICY_CONTEXT("2", "", "匹配文字"),
	MATCH_POLICY_TIME_AND_CONTEXT("3", "", "配置文字时间"),

	;
	private MatchPolicyEnum(String code, String flag, String description) {
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
	
	public static MatchPolicyEnum getEnumByCode(String code){
		for(MatchPolicyEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
