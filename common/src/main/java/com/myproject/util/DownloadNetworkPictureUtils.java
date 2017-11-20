package com.myproject.util;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载网络图片
 * @author twy
 *
 */
public final class DownloadNetworkPictureUtils {

	/**
	 * 下载图片
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public static PictureInfo download(String urlStr) throws Exception {
		HttpURLConnection conn = null;
		InputStream inStream = null;
		try {
			//new一个URL对象
			URL url = new URL(urlStr);
			//打开链接
			conn = (HttpURLConnection)url.openConnection();
			//设置请求方式为"GET"
			conn.setRequestMethod("GET");
			//超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//通过输入流获取图片数据
			inStream = conn.getInputStream();
			//得到图片的二进制数据，以二进制封装得到数据，具有通用性
			byte[] data = readInputStream(inStream);
			//new一个文件对象用来保存图片，默认保存当前工程根目录
			PictureInfo pictureInfo = new PictureInfo();
			pictureInfo.data = data;
			String contentType = conn.getContentType();
			if (contentType != null) {
				contentType = contentType.toLowerCase();
				if (contentType.indexOf("png") >= 0) {
					pictureInfo.format = "png";
				} else if (contentType.indexOf("jpeg") >= 0) {
					pictureInfo.format = "jpg";
				} else if (contentType.indexOf("bmp") >= 0) {
					pictureInfo.format = "bmp";
				}
			}
			return pictureInfo;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (inStream != null) inStream.close();
			} catch (Exception e2) {
			}
			try {
				if (conn != null) conn.disconnect();
			} catch (Exception e2) {
			}
		}
	}
	/**
	 * 保存图片
	 * @param pictureInfo 图片信息
	 * @param parent 父目录
	 * @param name 文件名（没有格式）
	 * @return 文件名
	 * @throws IOException
	 */
	public static String saveFile(PictureInfo pictureInfo, File parent, String name) throws IOException {
		File file = new File(parent, name + "." + pictureInfo.format);
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if (!file.exists()) file.createNewFile();
		FileUtils.writeByteArrayToFile(file, pictureInfo.data);
		return file.getName();
	}
	/**
	 * 下载并保存图片
	 * @param urlStr url
	 * @param parent 父目录
	 * @param name 文件名（没有格式）
	 * @return 文件名
	 * @throws IOException
	 * @throws Exception
	 */
	public static String saveFile(String urlStr, File parent, String name) throws IOException, Exception {
		return saveFile(download(urlStr), parent, name);
	}
	
	private static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		//每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		//使用一个输入流从buffer里把数据读取出来
		while( (len=inStream.read(buffer)) != -1 ){
			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		//关闭输入流
		inStream.close();
		//把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	
	/**
	 * 图片信息
	 * @author twy
	 *
	 */
	public static class PictureInfo {
		public String format;
		public byte[] data;
	}
	
}
