package com.myproject.interceptor.annotation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * 描述java对象的数据库映射信息（数据库表的映射、字段的映射）
 * 
 *  @author cssuger@163.com
 * 
 */
public class TableMapper {

    private Annotation tableMapperAnnotation;
    
    private Map<String, FieldMapper> fieldMapperCache;

    private List<FieldMapper> fieldMapperList;
    
    private SequenceMapper sequenceMapper;

    public List<FieldMapper> getFieldMapperList() {
        return fieldMapperList;
    }

    public void setFieldMapperList(List<FieldMapper> fieldMapperList) {
        this.fieldMapperList = fieldMapperList;
    }

    public Annotation getTableMapperAnnotation() {
        return tableMapperAnnotation;
    }

    public void setTableMapperAnnotation(Annotation tableMapperAnnotation) {
        this.tableMapperAnnotation = tableMapperAnnotation;
    }

	public SequenceMapper getSequenceMapper() {
		return sequenceMapper;
	}

	public void setSequenceMapper(SequenceMapper sequenceMapper) {
		this.sequenceMapper = sequenceMapper;
	}

	public Map<String, FieldMapper> getFieldMapperCache() {
        return fieldMapperCache;
    }

    public void setFieldMapperCache(Map<String, FieldMapper> fieldMapperCache) {
        this.fieldMapperCache = fieldMapperCache;
    }

	@Override
	public String toString() {
		return "TableMapper [tableMapperAnnotation=" + tableMapperAnnotation
				+ ", fieldMapperCache=" + fieldMapperCache
				+ ", fieldMapperList=" + fieldMapperList + ", sequenceMapper="
				+ sequenceMapper + "]";
	}

    
}
