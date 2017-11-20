package com.myproject.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Locale;
/**
 * @author lc_xin.
 * @date 2017/4/25
 */
public class DESUtil {

    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    /**
     * 解密
     * @param key 秘钥
     * @param data 需要解密的字符串
     * @return
     */
    public static String decode(String data,String key) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(byte2hex(data.getBytes())),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二进制转化成16进制
     *
     * @param b
     * @return
     */
    private static byte[] byte2hex(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static byte[] toByteArray(String hexString) {
        if (hexString == null)
            throw new IllegalArgumentException(
                    "this hexString must not be empty");
        hexString = hexString.toLowerCase(Locale.getDefault());
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 加密
     * @param key 秘钥
     * @param data 需要加密的字符串
     * @return
     */
    public static String encode(String data,String key) {
        if (data == null)
            return null;
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("utf-8"));
            return byte2String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return data;
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2String(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase(Locale.CHINA);
    }

    public static void main(String[] args) throws Exception{
        String str= "6bNzubOa8ExpS+\\/nEMyB6g==";
        String aomen = encode(str, "aomenshoujiaxianshangduchangshangxiankaiyele");
        System.out.println(aomen);
        System.out.println(decode(aomen,"aomenshoujiaxianshangduchangshangxiankaiyele"));
    }
}
