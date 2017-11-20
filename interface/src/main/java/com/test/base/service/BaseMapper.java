package com.test.base.service;


import com.test.base.entity.ValueObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<E extends ValueObject> {

	public int add(E entity);
	
	public int update(E entity);
	
	public E get(String id);
	
	public int delete(@Param("id") String id);
	
	public int deletes(@Param("ids") String[] ids);
	
	public List<E> query(@Param("dto") E entityDto, @Param("beginNum") int beginNum, @Param("rowNum") int rowNum);
	
	public int count(@Param("dto") E entityDto);
	
}
