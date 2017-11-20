package com.test.common.service;


/**
 * @author lc_xin.
 * @date 2016年5月7日
 */
public interface SendsmsService{

    boolean sendSms(String phoneNo, String content) throws Exception ;

    //发送激活卡券验证码
    String sendlykActivateCardValidate(String mobile) throws Exception ;

    //发送激活码
    String sendlykMessage(String mobile, String message, String productName) throws Exception ;

}
