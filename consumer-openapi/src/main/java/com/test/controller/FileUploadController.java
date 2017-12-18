package com.test.controller;

import com.test.common.entity.ImageGroup;
import com.test.common.enums.ImageConfigEnum;
import com.test.service.FileUploadService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author twy
 *
 */
@Controller
@RequestMapping("/lyk")
public class FileUploadController {

	static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 图片上传
	 * @param
	 * @param type
	 * @param multipartFile
	 * @param response
	 */
	@RequestMapping(value = "/image/upload.html", method = RequestMethod.POST)
	public void imageUpload(String type, @RequestParam("picture")MultipartFile multipartFile, 
			HttpServletResponse response) throws Exception {
		Map<String, Object> resObject = new HashMap<String, Object>();
		try {
			if (type != null
					&& multipartFile != null && !multipartFile.isEmpty()) {
				if ("activate".equals(type)) {
					ImageGroup imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, "activate");
					resObject.put("imageGroup", imageGroup);
				}else if ("bluePrint".equals(type)) {
					ImageGroup imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, "bluePrint");
					resObject.put("imageGroup", imageGroup);
				}else if ("activateCard".equals(type)) {
					ImageGroup imageGroup  = fileUploadService.imageHandle(multipartFile, ImageConfigEnum.member_logo_url, "activateCard");
					resObject.put("imageGroup", imageGroup);
				}else {
					ImageGroup imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, "other");
					resObject.put("imageGroup", imageGroup);
				}
			}
		} catch (Exception e) {
			logger.warn("imageUpload", e);
			throw e;
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json; charset=utf-8"); 
		try {
			response.getWriter().write(JSONObject.fromObject(resObject, new JsonConfig()).toString());
		} catch (Exception e) {
			logger.warn("imageUpload", e);
			throw e;
		}
	}
	@ResponseBody
	@RequestMapping(value = "deletePic")
	public JSONObject deletePic(String type, String strFileName){
		JSONObject res = new JSONObject();
		try{
			Boolean flag = fileUploadService.deletePic(type, strFileName);
			if(flag){
				res.put("count","1");
			}else {
				res.put("errorMsg","删除失败");
			}
			return res;
		}catch (Exception e){
			e.printStackTrace();
			logger.info("删除图片失败！",e);
			res.put("errorMsg","删除失败");
			return res;
		}
	}
}
