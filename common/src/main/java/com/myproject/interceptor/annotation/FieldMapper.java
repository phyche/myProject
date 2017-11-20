package com.myproject.interceptor.annotation;

import org.apache.ibatis.type.JdbcType;

/**
 * 字段映射类，用于描述java对象字段和数据库表字段之间的对应关系
 * 
 * @author cssuger@163.com
 * 
 */
public class FieldMapper {
    /**
     * Java对象字段名
     */
    private String fieldName;
    /**
     * 数据库表字段名
     */
    private String dbFieldName;
    /**
     * 数据库字段对应的jdbc类型
     */
    private JdbcType jdbcType;
    
    //seq名称，主要针对oracle的
    private String seqName;
    
    

    public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

	@Override
	public String toString() {
		return "FieldMapper [fieldName=" + fieldName + ", dbFieldName="
				+ dbFieldName + ", jdbcType=" + jdbcType + ", seqName="
				+ seqName + "]";
	}
    
    
}
