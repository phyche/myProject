package com.myproject.util;

public class StringUtil {

    /**
     * 隐藏构造函数
     */
    private StringUtil() {

    }

    /**
     * String null-->""
     * 
     * @param value 传入的字符串
     * @return String null-->""
     */
    public static String nvl(String value) {
	if (value == null) {
	    return "";
	} else {
	    return value.trim();
	}
    }

    /**
     * @param value
     *            字符串
     * @return String ""-->null
     * 
     */
    public static String nvlNull(String value) {
	if (value == null || "".equals(value.trim())) {
	    return null;
	} else {
	    return value.trim();
	}
    }

    /**
     * 两个字符串比较
     * 
     * @param s1
     *            字符串1
     * @param s2
     *            字符串2
     * @return 如果两个字符串相同返回true，否则返回false
     * 
     */
    public static boolean isEqual(String s1, String s2) {
	if (s1 == null && s2 == null) {
	    // 两个字符串都是null，返回true
	    return true;
	}
	if (s1 == null) {
	    return false;
	}
	if (s2 == null) {
	    return false;
	}
	return s1.equals(s2);
    }

    /**
     * 判断字符串是否为null或者空串
     * 
     * @param value 传入的字符串
     * @return 如果为null或者空串，返回true，否则返回false
     */
    public static boolean isEmpty(String value) {
	if (value == null || "".equals(value.trim())) {
	    return true;
	} else {
	    return false;
	}
    }
    
    /**
     * 返回加密的银行账号作显示
     * @param memberBankAcct
     * @return
     */
    public static String getMemberBankAcctAlias(String memberBankAcct) {
    	int l = memberBankAcct.length();
		return "********"+memberBankAcct.substring(l-4);
	}
	public static String appendStringFix0Left(String data, int count) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<(count-data.length());i++){
			sb.append("0");
		}
		sb.append(data);
		return sb.toString();
	}

	public static String appendStringFixSpaceRight(String data, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(data);
		for(int i = 0;i<(count-data.length());i++){
			sb.append(" ");
		}
		return sb.toString();
	}
	public static String appendStringFixRightByKey(String data, int count,String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(data);
		for(int i = 0;i<(count-data.length());i++){
			sb.append(key);
		}
		return sb.toString();
	}
/*	public static byte check(byte[] msg ){
		byte check = 0;
		int msgLen = msg.length;
		for (int i=4;i<msgLen;i++) {
			if(i == 0){
				check =msg[i];
			}else{
				check = (byte)(check ^ msg[i]);
			}
		}
		check = (byte)(check ^ 0x33);
		return check;
	}*/

	public static byte check(byte[] msg ){
		byte check = 0;
		int msgLen = msg.length;
		for (int i=0;i<msgLen;i++) {
			if(i == 0){
				check =msg[i];
			}else{
				check = (byte)(check ^ msg[i]);
			}
		}
		return check;
	}
	public static String fix0leftAndSub(String data, int count) {
		StringBuilder sb = new StringBuilder();
		if(data.length()<count){
			for(int i = 0;i<(count-data.length());i++){
				sb.append("0");
			}
		}else{
			data = data.substring(data.length()-count);
		}
		sb.append(data);
		return sb.toString();
	}
	public static boolean isNormalChar(String s){
		if(s!=null && !s.matches("^[0-9a-zA-Z]*$")){
			return false;
		}
		return true;
	}
}
