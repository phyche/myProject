package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	

	/**
	 * 跳转到登录界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String toLoginPage(Model model) {
		logger.info("toLoginPage");
		return "login";
	}

}
