package com.test.entity;
/**
 * 带结果列表回应对象
 * @author twy
 *
 */
public class ResponeResultListVO extends ResponeVO {

	/**
	 * 结果列表
	 */
	//private Object resultList;
	private Object resultList;
	
	public ResponeResultListVO() {
	}

	public ResponeResultListVO(Object resultList) {
		this.resultList = resultList;
	}
	public Object getResultList() {
		return resultList;
	}

	public void setResultList(Object resultList) {
		this.resultList = resultList;
	}
}
