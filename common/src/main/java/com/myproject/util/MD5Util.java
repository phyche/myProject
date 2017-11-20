package com.myproject.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * @author maqiang
 *
 */
public class MD5Util {

	/**
	 * 
	 * @Title: getMD5 
	 * @Description: 字符串加密 
	 * @param @param value
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getMD5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] e = md.digest(value.getBytes());
            return toHex(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return value;
        }
    }
 
	/**
	 * 
	 * @Title: getMD5 
	 * @Description:  加密操作 
	 * @param @param bytes
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws
	 */
    public static String getMD5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] e = md.digest(bytes);
            return toHex(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
 
    private static String toHex(byte bytes[]) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xff);
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
 
        return hs.toString();
    }
    public static String encrypt(String msg, String charset)
            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        StringBuffer signMsg = new StringBuffer();

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();

        byte[] inputBytes = msg.getBytes(charset);
        messageDigest.update(inputBytes);
        byte[] outputBytes = messageDigest.digest();

        for (int i = 0; i < outputBytes.length; i++) {
            if (Integer.toHexString(0xFF & outputBytes[i]).length() == 1) {
                signMsg.append("0").append(Integer.toHexString(0xFF & outputBytes[i]));
            }
            else {
                signMsg.append(Integer.toHexString(0xFF & outputBytes[i]));
            }
        }
        return signMsg.toString();
    }
    public static String md5(String str) {
        try {
            return encrypt(str, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("MD5失败", e);
        }
    }
    public static void main(String[] args) {
		System.out.println(MD5Util.getMD5("123456"));
	}
}
