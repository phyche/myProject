/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * WinPrize.java
 */
package com.test.base.entity;

import java.io.Serializable;
import java.util.Date;

public class PaySystemMemo extends ValueObject implements Serializable{
	private static final long serialVersionUID = -4870175829136713431L;
	private String paySysNo;
	private String reqSource;
	private Date requestTime;
	private Date lastUpdateTime;
	private Integer status;
	private String remark;
	private String amount;
	private String aliascode;
	private String aliascodeType;
	private String aliascodeBalance;
	private String paybank;
	private String bankcardno;
	private String banktransseq;
	private String loginid;
	private String actualamount;
	private String thirdPartyId;
	private String stateCode;
	private Date bankPayTime;
	private String paytype;
	private String bankName;
	private String refundOrder;
	private String refundType;
	private String reqBizType;
	private String redPacketName;
	private String redPacketStatus;
	private String mark;
	/**
	 * 转值的时候，记录接收人的loginName
	 */
	private String receivedLoginName;

	/**
	 * 转值的时候，记录接收人的aliascode
	 */
	private String receivedAliascode;

	private String transtype;

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getReqSource() {
		return reqSource;
	}

	public void setReqSource(String reqSource) {
		this.reqSource = reqSource;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAliascode() {
		return aliascode;
	}

	public void setAliascode(String aliascode) {
		this.aliascode = aliascode;
	}

	public String getAliascodeType() {
		return aliascodeType;
	}

	public void setAliascodeType(String aliascodeType) {
		this.aliascodeType = aliascodeType;
	}

	public String getAliascodeBalance() {
		return aliascodeBalance;
	}

	public void setAliascodeBalance(String aliascodeBalance) {
		this.aliascodeBalance = aliascodeBalance;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public String getBankcardno() {
		return bankcardno;
	}

	public void setBankcardno(String bankcardno) {
		this.bankcardno = bankcardno;
	}

	public String getBanktransseq() {
		return banktransseq;
	}

	public void setBanktransseq(String banktransseq) {
		this.banktransseq = banktransseq;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getActualamount() {
		return actualamount;
	}

	public void setActualamount(String actualamount) {
		this.actualamount = actualamount;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Date getBankPayTime() {
		return bankPayTime;
	}

	public void setBankPayTime(Date bankPayTime) {
		this.bankPayTime = bankPayTime;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRefundOrder() {
		return refundOrder;
	}

	public void setRefundOrder(String refundOrder) {
		this.refundOrder = refundOrder;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getReqBizType() {
		return reqBizType;
	}

	public void setReqBizType(String reqBizType) {
		this.reqBizType = reqBizType;
	}

	public String getRedPacketName() {
		return redPacketName;
	}

	public void setRedPacketName(String redPacketName) {
		this.redPacketName = redPacketName;
	}

	public String getRedPacketStatus() {
		return redPacketStatus;
	}

	public void setRedPacketStatus(String redPacketStatus) {
		this.redPacketStatus = redPacketStatus;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getReceivedLoginName() {
		return receivedLoginName;
	}

	public void setReceivedLoginName(String receivedLoginName) {
		this.receivedLoginName = receivedLoginName;
	}

	public String getReceivedAliascode() {
		return receivedAliascode;
	}

	public void setReceivedAliascode(String receivedAliascode) {
		this.receivedAliascode = receivedAliascode;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	@Override
	public String toString() {
		return "PaySystemMemo{" +
				"paySysNo='" + paySysNo + '\'' +
				", reqSource='" + reqSource + '\'' +
				", requestTime=" + requestTime +
				", lastUpdateTime=" + lastUpdateTime +
				", status=" + status +
				", remark='" + remark + '\'' +
				", amount='" + amount + '\'' +
				", aliascode='" + aliascode + '\'' +
				", aliascodeType='" + aliascodeType + '\'' +
				", aliascodeBalance='" + aliascodeBalance + '\'' +
				", paybank='" + paybank + '\'' +
				", bankcardno='" + bankcardno + '\'' +
				", banktransseq='" + banktransseq + '\'' +
				", loginid='" + loginid + '\'' +
				", actualamount='" + actualamount + '\'' +
				", thirdPartyId='" + thirdPartyId + '\'' +
				", stateCode='" + stateCode + '\'' +
				", bankPayTime=" + bankPayTime +
				", paytype='" + paytype + '\'' +
				", bankName='" + bankName + '\'' +
				", refundOrder='" + refundOrder + '\'' +
				", refundType='" + refundType + '\'' +
				", reqBizType='" + reqBizType + '\'' +
				", redPacketName='" + redPacketName + '\'' +
				", redPacketStatus='" + redPacketStatus + '\'' +
				", mark='" + mark + '\'' +
				", receivedLoginName='" + receivedLoginName + '\'' +
				", receivedAliascode='" + receivedAliascode + '\'' +
				", transtype='" + transtype + '\'' +
				'}';
	}
}
