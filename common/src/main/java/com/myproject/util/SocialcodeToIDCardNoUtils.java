package com.myproject.util;

public class SocialcodeToIDCardNoUtils {

	public static String praseToIDCardNo(String src) {
		if(src!=null&&src!=""){
			String trimSrc = src.trim();
			if(trimSrc.length()>=20){
				if(trimSrc.endsWith("810")){
					return trimSrc.substring(0, 17)+trimSrc.substring(17, trimSrc.length()).replace("810", "X");
				}else{
					return src.substring(0, 18);
				}
			}
			return trimSrc;
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(SocialcodeToIDCardNoUtils.praseToIDCardNo("12345678901234567810"));
	}
}
