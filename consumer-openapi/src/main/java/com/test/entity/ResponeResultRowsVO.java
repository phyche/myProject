package com.test.entity;
/**
 * 带结果列表回应对象
 * @author twy
 *
 */
public class ResponeResultRowsVO extends ResponeVO {

	/**
	 * 结果列表
	 */
	//private Object resultList;
	private ResultList resultList;
	
	public ResponeResultRowsVO(ResultList resultList) {
		super();
		this.resultList=resultList;
	}

	public ResultList getResultList() {
		return resultList;
	}

	public void setResultList(ResultList resultList) {
		this.resultList = resultList;
	}
}
