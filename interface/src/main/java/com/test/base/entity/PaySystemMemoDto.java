/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * SysMessageDto.java
 */
package com.test.base.entity;

import com.test.common.utils.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PaySystemMemoDto extends PaySystemMemo implements ValueObjectDTO{
	private static final long serialVersionUID = 1L;
	private Date requestTimeBegin;
	private Date requestTimeEnd;
	private Date bankPayTimeBegin;
	private Date bankPayTimeEnd;
	private String lastUpdateTimeBegin;
	private String lastUpdateTimeEnd;;
	private List<String> notEqualRemarkList;
	private List<String> notInRefundType;
	private List<String> notInReqsource;
	private List<String> loginInfoList;
	private List<String> memberInfoList;

	public List<String> getLoginInfoList() {
		return loginInfoList;
	}

	public void setLoginInfoList(List<String> loginInfoList) {
		this.loginInfoList = loginInfoList;
	}

	public List<String> getNotInReqsource() {
		return notInReqsource;
	}

	public void setNotInReqsource(List<String> notInReqsource) {
		this.notInReqsource = notInReqsource;
	}

	public List<String> getNotInRefundType() {
		return notInRefundType;
	}

	public void setNotInRefundType(List<String> notInRefundType) {
		this.notInRefundType = notInRefundType;
	}

	private Date startTime;
	private Date endTime;

	private Integer peoples;

	public Date getBankPayTimeBegin() {
		return bankPayTimeBegin;
	}

	public void setBankPayTimeBegin(Date bankPayTimeBegin) {
		this.bankPayTimeBegin = bankPayTimeBegin;
	}

	public Date getBankPayTimeEnd() {
		return bankPayTimeEnd;
	}

	public void setBankPayTimeEnd(Date bankPayTimeEnd) {
		this.bankPayTimeEnd = bankPayTimeEnd;
	}

	public Date getRequestTimeBegin() {
		return requestTimeBegin;
	}

	public void setRequestTimeBegin(Date requestTimeBegin) {
		this.requestTimeBegin = requestTimeBegin;
	}

	public Date getRequestTimeEnd() {
		return requestTimeEnd;
	}

	public void setRequestTimeEnd(Date requestTimeEnd) {
		this.requestTimeEnd = requestTimeEnd;
	}

	public List<String> getNotEqualRemarkList() {
		return notEqualRemarkList;
	}

	public void setNotEqualRemarkList(List<String> notEqualRemarkList) {
		this.notEqualRemarkList = notEqualRemarkList;
	}

	public String getLastUpdateTimeBegin() {
		return lastUpdateTimeBegin;
	}

	public void setLastUpdateTimeBegin(String lastUpdateTimeBegin) {
		if(!StringUtil.isEmpty(lastUpdateTimeBegin)){
			this.setLastUpdateTimeEnd(lastUpdateTimeBegin+" 23:59:59");
		}
		this.lastUpdateTimeBegin = lastUpdateTimeBegin;
	}

	public String getLastUpdateTimeEnd() {
		return lastUpdateTimeEnd;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getPeoples() {
		return peoples;
	}

	public void setPeoples(Integer peoples) {
		this.peoples = peoples;
	}

	public List<String> getMemberInfoList() {
		return memberInfoList;
	}

	public void setMemberInfoList(List<String> memberInfoList) {
		this.memberInfoList = memberInfoList;
	}

	/**
	 *需要正序字段名
	 */
	private List<String> asc;
	/**
	 *需要逆序字段名
	 */
	private List<String> desc;
	/**
	 *排序SQL
	 */
	private String orders;
    
    /**
	 *  添加需要正序字段名
	 */
	public void addAsc(String col){
		if(asc==null){
			asc = new LinkedList<String>();
		}
		asc.add(col);
	}
	
	/**
	 *  清空需要正序字段名
	 */
	public void cleanAsc(){
		asc = null;
	}
	
	/**
	 *  添加需要逆序字段名
	 */
	public void addDesc(String col){
		if(desc==null){
			desc = new LinkedList<String>();
		}
		desc.add(col);
	}
	
	/**
	 *  清空需要逆序字段名
	 */
	public void cleanDesc(){
		desc = null;
	}
	
	/**
	 *  如果排序SQL为空根据需要正逆序的字段名拼接排序SQL
	 */
	public String  getOrders() {
		StringBuilder orderStr = null;
		StringBuilder ascStr;
		StringBuilder descStr;
		if(orders==null){
			if(asc!=null){
				ascStr = new StringBuilder();
				orderStr = new StringBuilder();
				String pex = "";
				for(String a : asc){
					ascStr.append(pex+a);
					pex = ",";
				}
				orderStr.append(ascStr.toString()+" ASC");
			}
			if(desc!=null){
				descStr = new StringBuilder();
				String pex = "";
				for(String d : desc){
					descStr.append(pex+d);
					pex = ",";
				}
				if(orderStr==null){
					orderStr = new StringBuilder();
					orderStr.append(descStr.toString()+" DESC");
				}else{
					orderStr.append(","+descStr.toString()+" DESC");
				}
			}
			orders = orderStr!=null?orderStr.toString():null;
		}
		
		return orders;
	}
	
	/**
	 *  设置排序SQL
	 */
	public void setOrders(String orders) {
		this.orders = orders;
	}
	private Date createDateBefore;
	private Date createDateAfter;

    public Date getCreateDateBefore() {
        return this.createDateBefore;
    }
    public void setCreateDateBefore(Date createDateBefore) {
        this.createDateBefore = createDateBefore;
    }

    public Date getCreateDateAfter() {
        return this.createDateAfter;
    }
    public void setCreateDateAfter(Date createDateAfter) {
        this.createDateAfter = createDateAfter;
    }
	
}
