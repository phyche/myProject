package com.test.service.impl;

import com.myproject.util.DownloadNetworkPictureUtils;
import com.myproject.util.FileUtil;
import com.test.base.exception.YIXUNUNCheckedException;
import com.test.common.entity.ImageGroup;
import com.test.common.enums.ImageConfigEnum;
import com.test.common.enums.SnEnum;
import com.test.common.hanlder.ImageHanlder;
import com.test.common.utils.StringUtil;
import com.test.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {
	
/*	@Value("${imagePath}")
	private String  imagePath;*/
	@Value("#{configProperties['imagePath']}")
	protected String  imagePath;
	@Override
	public ImageGroup imageHandle(MultipartFile file, ImageConfigEnum configEnum, String directory) {
		return handle(file, configEnum.getLargeWidth(),
				configEnum.getLargeHeight(), configEnum.getMediumWidth(),
				configEnum.getMediumHeight(), configEnum.getThumbnailWidth(),
				configEnum.getThumbnailHeight(), directory);
	}
	
	/**
	 * 保存图片
	 * @param file
	 * @return
	 */
	public ImageGroup imageHandle(MultipartFile file, long largeWidth,
								  long largeHeiht, long mediumWidth, long mediumHeiht,
								  long thumbnailWidth, long thumbnailHeiht) {
		return handle(file, largeWidth, largeHeiht, mediumWidth, mediumHeiht, thumbnailWidth, thumbnailHeiht, null);
	}
	/**
	 * 
	 * @param file
	 * @param largeWidth
	 * @param largeHeiht
	 * @param mediumWidth
	 * @param mediumHeiht
	 * @param thumbnailWidth
	 * @param thumbnailHeiht
	 * @param directory
	 * @return
	 */
	private ImageGroup handle(MultipartFile file, long largeWidth,
							  long largeHeiht, long mediumWidth, long mediumHeiht,
							  long thumbnailWidth, long thumbnailHeiht, String directory) {
		if (file == null || file.isEmpty()) {
			throw new YIXUNUNCheckedException(SnEnum.IMG_NO_EXIST.getCode());
		}
		String directoryStr = directory;
		if (directoryStr == null || directoryStr.trim().length() == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			directoryStr = format.format(Calendar.getInstance().getTime());
		}
		String savePathStr = imagePath + (imagePath.endsWith("/") ? "" : "/")
				+ "images/" + directoryStr + "/";
		String reqPathStr = "/images/" + directoryStr + "/";
		ImageGroup imageModel = ImageHanlder.build(file, savePathStr,
				reqPathStr, largeWidth, largeHeiht, mediumWidth, mediumHeiht,
				thumbnailWidth, thumbnailHeiht, 50);
		return imageModel;
	}

	@Override
	public String downloadPic(String url, String directory) throws Exception {
		String directoryStr = directory;
		if (directoryStr == null || directoryStr.trim().length() == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			directoryStr = format.format(Calendar.getInstance().getTime());
		}
		String fileName = UUID.randomUUID().toString();
		
		String savePathStr = imagePath + (imagePath.endsWith("/") ? "" : "/")
				+ "images/" + directoryStr + "/";
		
		String f = DownloadNetworkPictureUtils.saveFile(url, new File(savePathStr), fileName);
		
		String reqPathStr = "/images/" + directoryStr + "/";
		return reqPathStr + f;
	}
	
	public String downloadPic(byte[] pic, String directory) throws Exception {
		String directoryStr = directory;
		if (directoryStr == null || directoryStr.trim().length() == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			directoryStr = format.format(Calendar.getInstance().getTime());
		}
		String fileName = UUID.randomUUID().toString();
		
		String savePathStr = imagePath + (imagePath.endsWith("/") ? "" : "/")
				+ "images/" + directoryStr + "/";
		DownloadNetworkPictureUtils.PictureInfo pictureInfo=	new DownloadNetworkPictureUtils.PictureInfo();
		pictureInfo.format="jpg";
		pictureInfo.data=pic;
		String f = DownloadNetworkPictureUtils.saveFile(pictureInfo, new File(savePathStr), fileName);
		
		String reqPathStr = "/images/" + directoryStr + "/";
		return reqPathStr + f;
	}

	public Boolean deletePic(String type, String fileName) throws Exception {
		boolean flag =false;
		String directoryPath = imagePath + (imagePath.endsWith("/") ? "" : "/") + "images/" + type + "/"+fileName;

		String[] paths = new String[4];
		paths[0] = directoryPath+"-large.jpg";
		paths[1] = directoryPath+"-medium.jpg";
		paths[2] = directoryPath+"-thumbnail.jpg";
//		paths[3] = directoryPath+"-source.jpg";
		for(String path :paths){
			if(!StringUtil.isEmpty(path)){
				flag = FileUtil.delete(path);
			}
		}
		return flag;
	}
	
}
