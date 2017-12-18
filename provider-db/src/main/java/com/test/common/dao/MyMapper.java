/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * CardBlackListMapper.java
 */
package com.test.common.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyMapper {
	public List queryBySql(@Param("sql") String sql);
}
