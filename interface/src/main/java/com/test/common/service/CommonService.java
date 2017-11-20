package com.test.common.service;

import com.test.common.entity.ImageGroup;
import com.test.common.enums.ImageConfigEnum;
import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
	/**
	 * 保存图片
	 * @param file
	 * @return
	 */
	public ImageGroup imageHandle(MultipartFile file, long largeWidth, long largeHeiht, long mediumWidth, long mediumHeiht, long thumbnailWidth, long thumbnailHeiht);
	/**
	 * 保存图片
	 * @param file
	 * @param configEnum
	 * @param directory 目录
	 * @return
	 */
	public ImageGroup imageHandle(MultipartFile file, ImageConfigEnum configEnum, String directory);
	/**
	 * 下载网络图片到本地图片服务器
	 * @param url 网络图片
	 * @param directory 本地存在目录
	 * @return 本地图片服务器URL
	 * @throws Exception
	 */
	public String downloadPic(String url, String directory) throws Exception;
	/**
	 * 保存头像图片
	 * @param pic 图片字节
	 * @param directory 本地存在目录
	 * @return 本地图片服务器URL
	 * @throws Exception
	 */
	public String downloadPic(byte[] pic, String directory) throws Exception;
	/**
	 * 删除头像图片
	 * @param type 本地存在目录type
	 * @param fileName 文件名
	 * @return
	 * @throws Exception
	 */
	public Boolean deletePic(String type, String fileName)throws Exception;
}
