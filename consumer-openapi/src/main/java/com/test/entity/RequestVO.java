package com.test.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

public class RequestVO {
	
	private String serCode;
	private String dataMsg;
	private String token;
	private String ak;
	private String erminalCode;
	private String sign;
	
	public String getSerCode() {
		return serCode;
	}
	public void setSerCode(String serCode) {
		this.serCode = serCode;
	}
	public String getDataMsg() {
		return dataMsg;
	}
	public void setDataMsg(String dataMsg) {
		this.dataMsg = dataMsg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAk() {
		return ak;
	}
	public void setAk(String ak) {
		this.ak = ak;
	}
	public String getErminalCode() {
		return erminalCode;
	}
	public void setErminalCode(String erminalCode) {
		this.erminalCode = erminalCode;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * 校验签名
	 * @return
	 */
	public boolean verifySignature() {
		if (sign == null || sign.trim().length() == 0) {
			return false;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("serCode=" + serCode);
		buffer.append("&dataMsg=" + dataMsg);
		buffer.append("&token=" + token);
		buffer.append("&ak=" + ak);
		buffer.append("&erminalCode=" + erminalCode);
		// 比较忽略大小写
		return sign.equalsIgnoreCase(DigestUtils.md5Hex(buffer.toString()));
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("serCode=" + serCode);
		buffer.append("&dataMsg=" + dataMsg);
		buffer.append("&token=" + token);
		buffer.append("&ak=" + ak);
		buffer.append("&erminalCode=" + erminalCode);
		buffer.append("&sign=" + sign);
		return buffer.toString();
	}

	/**
	 * 校验版本
	 * @return
	 */
	public boolean verifyVersion() {
		JSONObject jsonObject = JSON.parseObject(erminalCode);
		String version = (String) jsonObject.get("version");
		Integer str = Integer.valueOf(version.replaceAll("\\.", "").replaceAll(" ",""));
		String os = String.valueOf(jsonObject.get("os"));
		if("1".equals(os)){
			if (str<=2) {
				return true;
			}
		}
		return false;
	}
}
