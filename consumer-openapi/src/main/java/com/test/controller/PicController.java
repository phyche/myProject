package com.test.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.myproject.util.DESUtil;
import com.test.base.enums.RedisEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/pic")
public class PicController {

	private static final Logger logger = LoggerFactory.getLogger(PicController.class);

	@Autowired
	private RedisTemplate redisTemplate;
	@Value("#{configProperties['requestKey']}")
	private String requestKey;
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";
	//--kapcha验证码。
	private Properties props = new Properties();
	private Producer kaptchaProducer = null;
	private String sessionKeyValue = null;

	public PicController() {
		inikaptcha();
	}
	private void inikaptcha(){
		ImageIO.setUseCache(false);
		//设置宽和高。
		this.props.put(Constants.KAPTCHA_IMAGE_WIDTH, "200");
		this.props.put(Constants.KAPTCHA_IMAGE_HEIGHT, "60");
		//kaptcha.border：是否显示边框。
		this.props.put(Constants.KAPTCHA_BORDER, "no");
		//kaptcha.textproducer.font.color：字体颜色
		this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		//kaptcha.textproducer.char.space：字符间距
		this.props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "5");
		//设置字体。
		this.props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
		//this.props.put(Constants.KAPTCHA_NOISE_COLOR, "");
		//更多的属性设置可以在com.google.code.kaptcha.Constants类中找到。
		Config config1 = new Config(this.props);
		this.kaptchaProducer = config1.getProducerImpl();
		this.sessionKeyValue = config1.getSessionKey();
	}
	@RequestMapping(value = "/check/{key}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void check(HttpServletResponse response, HttpServletRequest request,
						@PathVariable String key) throws IOException {
		// flush it in the response
		logger.info("request key:{},requestKey:{}",key,requestKey);
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);
		key = DESUtil.decode(key,requestKey);
		String capText = this.kaptchaProducer.createText();
		BufferedImage bi = this.kaptchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		try {
//			存放redis
			redisTemplate.opsForValue().set(key+ RedisEnum.REDIS_KAPTCHA_KEY.getCode(),capText,2l, TimeUnit.MINUTES);
			ImageIO.write(bi, CAPTCHA_IMAGE_FORMAT, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null)out.close();
		}

	}

}
