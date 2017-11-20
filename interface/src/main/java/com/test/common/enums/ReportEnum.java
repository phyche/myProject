package com.test.common.enums;

public enum ReportEnum {
	QUERY_DATE_TYPE_DAY("day", "", "日"),
	QUERY_DATE_TYPE_MONTH("month", "", "月"),
	QUERY_DATE_TYPE_YEAR("year", "", "年"),

	//活动数据统计报表枚举
	ACT_ACCESS("1", "", "访问活动"),
	ACT_TEKE_PART_IN_DRAW("2", "", "参与抽奖")

	;
	private ReportEnum(String code, String flag, String description) {
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
	
	public static ReportEnum getEnumByCode(String code){
		for(ReportEnum e:values()){
			if(e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
