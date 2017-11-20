package com.test.common.utils;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class RandomUtils {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };


	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	public static String generateShortNum(int num) {
		String dateStr =String.valueOf(Calendar.getInstance().getTimeInMillis());
		return dateStr.substring(dateStr.length()-num);
	}
	public static int generateIntNum(int min,int max){
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	/**
	 * 获取16进制随机数
	 * @param len
	 * @return
	 */
	public static String randomHexString(int len)  {
		try {
			StringBuffer result = new StringBuffer();
			for(int i=0;i<len;i++) {
				result.append(Integer.toHexString(new Random().nextInt(16)));
			}
			return result.toString().toUpperCase();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return null;

	}
	public static void main(String[] args) {
		System.out.print(RandomUtils.randomHexString(1));
	}
}
