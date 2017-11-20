package com.myproject.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class EncryptUtils {

	/**
	 * 加密
	 * 
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @return
	 */
	public static String aesEncrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result); // 加密
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param content 待解密内容
	 * @param password 解密密钥
	 * @return
	 */
	public static String aesDecrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(parseHexStr2Byte(content));
			return new String(result, "utf-8"); // 加密
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(aesDecrypt("07662DA0030B2DF74ED05BB5DA4957C814953B17C290B893879F7BF1F4DAB8B341C324F0966E3AC5559F97F8BB33CD641F629094DD897272DAF735E9084A2CC8C4ECDC9603A101DB9568188BDC0568CA7692BFCE7140E8FB081C6688CCB6E74F11AE2B5EBB133547BE52C1E1BA48C56218DE078E3D99F2892298EEE758CF30A004545C029F958C4BB41BAB6C2539C1259597F642B9D7FA4E645EA76F0B31EE635CD838DD73143256D13AA86D0C9D04696FBD4CB4E9C4833EA45EED0019A09FB408B01C350455A396DEC18349A8A25525AE070D9D0776BDD41BBBB366891A8728382146F9C7041C4809561125050B00DF75E8FA77F18E6022E2155870058E41DC809ADFD811ED27CF814C2488BFFC7367192192E6CA9B077DFB76EE62F65D73F36665D75B50A0126AB96E0235D501E4B7D1290C8A0150DC5C4E2A4D5FF3DD1BE771ABEC5CA7B87D26E20CC396E1CB0BE36743E5B603665D847BB508D5AFA7700F428734F96AF6F84C4B2F79C08F6952E0D0E81E14D0108AF4D998F6DB9BAA67F69D8D2C9133436F5097E7D18A5150CAC577699D1584E497571FC6F2277394872F95F3683EEDD373C2D92A03818D999795A76C0713214D138921A10B55813B9004EF1F71635E913B1F7EB96E7BB6ED03CA36B8E109607E9CE8AFB44DE3475A0EE91E602DE27CC9410B6ABBECDF8958EC5B0B8A5965B32A1426188465C0B89C42E445976BFF4562A208A01383F8065EF0D9E37C81C16AE8C060366ADBED24929DB00A89FA56099B3ADF17967378245BF4AEB4023B369A306C94BE8E81568EE42B1D", "756F490AFE7D090267B9CAF28AD42020"));
	}
	
}
