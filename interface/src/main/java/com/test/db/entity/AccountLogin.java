package com.test.db.entity;

import com.test.base.entity.ValueObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by caiqi on 2016/11/21.
 */
public class AccountLogin extends ValueObject {

    private static final long serialVersionUID = -472394784118853307L;
    private String memberCode;
    private String loginName;
    private BigDecimal balance; /** 会员账户余额 */
    private String email;
    private String mobile;
    private String custName;
    private String idNo;
    private Date updatedate;
    private String token;
    private String address;
    private String nickname;
    /**
     *请求来源 1为智汇APP 2为医疗APP
     */
    private String reqBizType;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReqBizType() {
        return reqBizType;
    }

    public void setReqBizType(String reqBizType) {
        this.reqBizType = reqBizType;
    }
}
