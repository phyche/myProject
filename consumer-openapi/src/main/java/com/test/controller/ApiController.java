package com.test.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.myproject.util.DESUtil;
import com.myproject.util.DateUtil;
import com.myproject.util.EncryptUtils;
import com.test.base.enums.RedisEnum;
import com.test.common.entity.ImageGroup;
import com.test.common.enums.ImageConfigEnum;
import com.test.common.enums.ReqBizTypeEnum;
import com.test.common.utils.StringUtil;
import com.test.controller.base.AbstractController;
import com.test.controller.base.ApiMessageException;
import com.test.db.entity.AccountLoginDTO;
import com.test.entity.*;
import com.test.handler.ApiHandler;
import com.test.handler.TokenCheckable;
import com.test.mq.queue.send.QueueSender;
import com.test.service.FileUploadService;
import com.test.utils.NcvasApplicationContextUtils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/api")
public class ApiController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private RedisTemplate redisTemplate;
	@Resource
	QueueSender queueSender;

	@Value("#{configProperties['encryptKey']}")
	private String encryptKey;

	@Value("#{configProperties['requestKey']}")
	private String requestKey;

	@Value("#{configProperties['backupSerCode']}")
	private String backupSerCode;

	/**
	 * 图片上传请求路径
	 */
	@Value("#{configProperties['requestPath']}")
	public String  imageRequestPathStr;
	private String lykTokenPrefix,versionTypeLyk;
	@RequestMapping(value = "lyk.html", method = RequestMethod.POST)
	public void service(RequestVO requestVO, HttpServletRequest request, HttpServletResponse response) {
		ResponeVO responeVO = null;
		// 禁用缓存
		response.setHeader("Cache-Control", "no-cache"); // or no-store
		response.setHeader("Pragrma", "no-cache"); 
		response.setDateHeader("Expires", 0);
		String erminalCode = null;
		//日志跟踪号
		String traceId = DateUtil.getRandomTraceId();
		MDC.put("TRACE_ID",traceId);
		RpcContext.getContext().getAttachments().put("TRACE_ID",traceId);
		try {
			String appVersion = request.getHeader("appVersion");
			logger.info("用户版本号为:" + appVersion);
			if(appVersion == null || appVersion.trim().length() == 0) {
				throw new ApiMessageException(new ResponeVO(6, "请更新最新版本APP!"));
			}

			try {
				requestVO.setSerCode(DESUtil.decode(requestVO.getSerCode(),requestKey));
				requestVO.setDataMsg(DESUtil.decode(requestVO.getDataMsg(),requestKey));
				erminalCode = DESUtil.decode(requestVO.getErminalCode(), this.requestKey);
				requestVO.setErminalCode(erminalCode);
			}catch (Exception e){
				logger.info("解密请求数据错误:"+e);
				throw new ApiMessageException(ApiHandler.RESP_SYSTEM_ERROR);
			}

			String serCode = requestVO.getSerCode();
			String dataMsg = requestVO.getDataMsg();
			logger.info("请求业务所有参数==============>>>" + requestVO.toString());
			logger.info("请求业务编码SerCode==============>>>" + serCode);
			logger.info("请求业务内容DataMsg==============>>>" + dataMsg);
			logger.info("请求业务内容ErminalCode==============>>>" + erminalCode);
			String[] serCodes = this.backupSerCode.split(";");
			Set<String> set = new HashSet(Arrays.asList(serCodes));
			if(!set.contains(serCode)) {
				Map<String, Object> versionAppMap = this.verifyAppVersion(appVersion, erminalCode);
//				Map<String, Object> versionAppMap = this.verifyAppVersion(appVersion, requestVO.getErminalCode());
				if(versionAppMap != null) {
					throw new ApiMessageException(new ResponeResultVO(12, "请更新最新版本！", versionAppMap));
				}
			}

			if(!requestVO.verifySignature()) {
				// 签名信息错误
				throw new ApiMessageException(ApiHandler.RESP_SIGN_ERROR);
			}

			logger.info("业务编码:"+requestVO.getSerCode());
			ApiHandler handler = NcvasApplicationContextUtils.getApiHandlerClassMaps().get(requestVO.getSerCode());
			if (handler != null) {
				handler = NcvasApplicationContextUtils.getContext().getBean(handler.getClass());
				if (handler instanceof RequestVOAware) {
					((RequestVOAware) handler).setRequestVO(requestVO);
				}
				if(handler instanceof HttpServletRequestVO){
					((HttpServletRequestVO) handler).setHttpServletRequest(request);
				}
				if (handler instanceof TokenCheckable) {
					//校验版本
					//if(requestVO.verifyVersion()) {
					//	if("1".equals(JSON.parseObject(requestVO.getErminalCode()).get("os"))){//安卓
					//		throw new ApiMessageException(new ResponeVO(6, "请前往各大应用市场更新版本!") );
					//	}
					//}
					// 实现该接口，必须校验token
					if (requestVO.getToken() == null || requestVO.getToken().trim().length() == 0) {
						throw new ApiMessageException(ApiHandler.RESP_LOGOUT_ERROR);
					}

					String  Temp= (String)redisTemplate.opsForValue().get(lykTokenPrefix+requestVO.getToken());
					if (Temp == null) {
						throw new ApiMessageException(ApiHandler.RESP_LOGOUT_ERROR);
					}
					Map<String,Object> json = JSONObject.fromObject(Temp);
					String token =(String)json.get("token");
					if (token == null) {
						throw new ApiMessageException(ApiHandler.RESP_LOGOUT_ERROR);
					}
					AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
					accountLoginDTO.setToken(token);
					accountLoginDTO.setMemberCode((String)json.get("memberCode"));
					accountLoginDTO.setLoginName((String)json.get("loginName"));
					accountLoginDTO.setBalance(new BigDecimal((Integer) json.get("balance")));
					accountLoginDTO.setMobile((String)json.get("mobile"));
					accountLoginDTO.setEmail((String)json.get("email"));
					accountLoginDTO.setCustName((String)json.get("custName"));
					accountLoginDTO.setIdNo((String)json.get("idNo"));
					accountLoginDTO.setNickname((String)json.get("nickname"));
					/**（PS: 这里是否需要cookie, 先写上)*/
					accountLoginDTO.setCookie(EncryptUtils.aesDecrypt((String) json.get("cookie"),encryptKey));
					((TokenCheckable) handler).setMember(accountLoginDTO);
					logger.info("token:"+token);

				}
				Object paramsObject = null;
				Class<?> paramsClass = ((ApiHandler) handler).getRequestParams();
				if (paramsClass != null) {
					if (requestVO.getDataMsg() == null) {
						throw new ApiMessageException(ApiHandler.RESP_PARAMS_ERROR);
					}
					JSONObject jsonObject = JSONObject.fromObject(requestVO.getDataMsg());
					try {
						paramsObject = JSONObject.toBean(jsonObject, paramsClass);
					} catch (Exception e) {
						logger.warn("ApiController#service " + e.getMessage());
						throw new ApiMessageException(ApiHandler.RESP_PARAMS_ERROR);
					}
				} else if (!StringUtils.isBlank(requestVO.getDataMsg())) {
					paramsObject = JSONObject.fromObject(requestVO.getDataMsg());
				}
				((ApiHandler) handler).checkRequestParams(paramsObject);
				responeVO = ((ApiHandler) handler).doHandler(paramsObject);
			} else {
				responeVO = ApiHandler.RESP_UNKNOWN_SERVICE_CODE_ERROR;
			}
		} catch (ApiMessageException e) {
			logger.warn("ApiController#service " + e.getResponeVO());
			responeVO = e.getResponeVO();
		} catch (JSONException e) {
			logger.warn("ApiController#service " + e.getMessage());
			responeVO = ApiHandler.RESP_JSON_FORMAT_ERROR;
		} catch (Throwable e) {
			logger.warn("ApiController#service", e);
			responeVO = ApiHandler.RESP_SERVICE_BUSY_ERROR;
		}
		if (responeVO == null) {
			responeVO = ApiHandler.RESP_UNKNOWN_ERROR;
		}
		this.responseMessageToClient(responeVO, response);
	}

	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 图片上传
	 * @param token
	 * @param type
	 * @param multipartFile
	 * @param response
	 */
	@RequestMapping(value = "image/upload.html", method = RequestMethod.POST)
	public void fileUpload(String token, String type, @RequestParam("file")MultipartFile multipartFile,
						   HttpServletResponse response) throws IOException {
		ResponeVO responeVO = null;
		try {
			if (token != null && type != null
					&& multipartFile != null && !multipartFile.isEmpty()) {

				String  Temp= (String)redisTemplate.opsForValue().get(lykTokenPrefix+token );
				if(StringUtil.isEmpty(Temp)){
					throw new ApiMessageException(ApiHandler.RESP_LOGOUT_ERROR);
				}

				ImageGroup imageGroup = null;
				if ("0".equals(type)) {
					// 用户头像
					imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum.member_logo_url, "heads");
				} else if ("1".equals(type)) {
					// 卡券激活图片
					imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum.member_logo_url, "activateCard");
				} else if ("2".equals(type)) {
					// 晒图
					String dateStr = DateUtil.praseDate(new Date(), "yyyyMMdd");
					imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, "bluePrint/" + dateStr);
				}  else if ("3".equals(type)) {
					// 身份证照片
					imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, "sid");
				}else {
					imageGroup = fileUploadService.imageHandle(multipartFile, ImageConfigEnum._default, null);
				}
				if (imageGroup != null) {
					Map<String, String> result = new HashMap<String, String>();
					result.put("url", imageRequestPathStr + imageGroup.getSourceUrl());
					result.put("sourceHeight", String.valueOf(imageGroup.getSourceHeight()));
					result.put("sourceWidth", String.valueOf(imageGroup.getSourceWidth()));
					result.put("largeHeight", String.valueOf(imageGroup.getLargeHeight()));
					result.put("largeWidth", String.valueOf(imageGroup.getLargeWidth()));
					result.put("mediumHeight", String.valueOf(imageGroup.getMediumHeight()));
					result.put("mediumWidth", String.valueOf(imageGroup.getMediumWidth()));
					result.put("thumbnailHeight", String.valueOf(imageGroup.getThumbnailHeight()));
					result.put("thumbnailWidth", String.valueOf(imageGroup.getThumbnailWidth()));
					logger.info("lyk||fileUpload||返回的参数为：" + result);
					responeVO = new ResponeResultVO(result);
				}
			}
		} catch (ApiMessageException e) {
			logger.warn("ApiController#service " + e.getResponeVO());
			responeVO = e.getResponeVO();
		} catch (Exception e) {
			logger.warn("ApiController#fileUpload", e);
			responeVO = ApiHandler.RESP_UNKNOWN_ERROR;
		}
		if (responeVO == null) responeVO = new ResponeVO(10001, "图片上传失败");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json; charset=utf-8");
		response.getWriter().write(JSONObject.fromObject(responeVO, new JsonConfig()).toString());
	}

	/**
	 * 请求JSON回应
	 * @param responeVO
	 * @param response
	 */
	private void responseMessageToClient(ResponeVO responeVO, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json; charset=utf-8"); 
		try {
			JsonConfig config = new JsonConfig();
//			response.getWriter().write(JSONObject.fromObject(responeVO, config).toString());	//不加密返回
			response.setHeader("sign", DigestUtils.md5Hex(JSONObject.fromObject(responeVO, config).toString()));
			response.getWriter().write(DESUtil.encode(JSONObject.fromObject(responeVO, config).toString(),requestKey));
		} catch (Exception e) {
			logger.warn("ApiController#service", e);
			try {
//			response.getWriter().write("{\"responseCode\":3,\"responseDesc\":\"服务器繁忙\"}"); 	//不加密返回
			response.setHeader("sign", DigestUtils.md5Hex("{\"responseCode\":3,\"responseDesc\":\"服务器繁忙\"}"));
			response.getWriter().write(DESUtil.encode("{\"responseCode\":3,\"responseDesc\":\"服务器繁忙\"}",requestKey));
			} catch (IOException e1) {
				logger.warn("ApiController#service", e);
			}
		}
	}


	private Map<String,Object> verifyAppVersion(String appVersion,String erminalCode) {
		JsonConfig config = new JsonConfig();
		JSONObject json = JSONObject.fromObject(erminalCode, config);
		String os = String.valueOf(json.get("os"));
		String versionKey = (String)redisTemplate.opsForHash().get(RedisEnum.VERSION_KEY.getCode(),os+"_"+ versionTypeLyk);
		Integer version = Integer.valueOf(appVersion.replaceAll("\\.", "").replaceAll(" ",""));
		if(!StringUtil.isEmpty(versionKey)){
			JSONObject versionControl = JSONObject.fromObject(versionKey);
			return versionMap(version,versionControl);
		}
		return  null;
	}

	private Map<String,Object> versionMap(Integer version,JSONObject versionControl){
		Integer versionName = Integer.valueOf(String.valueOf(versionControl.get("versionName")).replaceAll("\\.", "").replaceAll(" ",""));//最新版本
		//ismust;是否必须更新 1为必须 2为推荐
		String ismust = String.valueOf(versionControl.get("ismust"));
		if(versionName>version && "1".equals(ismust)){//当前最新版本比app传过来的要高 还是必须更新的
			Map<String,Object> resultMap=new HashMap<String, Object>();
			if(versionControl!=null){
				resultMap.put("versionName",String.valueOf(versionControl.get("versionName")));
				resultMap.put("versionCode",Integer.valueOf(String.valueOf(versionControl.get("versionCode"))));
				resultMap.put("versionClient",String.valueOf(versionControl.get("versionClient")));
				resultMap.put("versiondes",String.valueOf(versionControl.get("versiondes")));
				resultMap.put("downloadUrl",String.valueOf(versionControl.get("downloadUrl")));
				resultMap.put("versionType",String.valueOf(versionControl.get("versionType")));
				resultMap.put("ismust", String.valueOf(versionControl.get("ismust")));
				return resultMap;
			}
		}
		return null;
	}

}
