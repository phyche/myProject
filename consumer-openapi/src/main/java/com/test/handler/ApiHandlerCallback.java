package com.test.handler;


import com.test.entity.ResponeVO;

public interface ApiHandlerCallback {

	ResponeVO callback() throws Exception;
	
}
