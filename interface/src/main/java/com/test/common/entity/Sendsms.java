package com.test.common.entity;


import com.test.base.entity.ValueObject;

/**
 * @author lc_xin.
 * @date 2016年5月7日
 */
public class Sendsms extends ValueObject {

	private static final long serialVersionUID = -1822683404008712113L;
	private String mobile;
	private String sendType;
	private String feedBackMsg;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getFeedBackMsg() {
		return feedBackMsg;
	}

	public void setFeedBackMsg(String feedBackMsg) {
		this.feedBackMsg = feedBackMsg;
	}
}