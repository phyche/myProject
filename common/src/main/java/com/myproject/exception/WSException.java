package com.myproject.exception;

/** 
  *@ClassName: WSException 
  *@Description: TODO 
  *@author: yanhewei@boco.com.cn 
  *@date: 2016年4月22日 下午3:08:46   
  */

public class WSException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107999795109334873L;
	
	private String code;
	
	private boolean flage;
	
	private String msg;

	public WSException(){
		
	}
	
	public WSException(String code,boolean flage,String msg){
		this.code = code;
		this.flage = flage;
		this.msg = msg;
	}
	
	
	public WSException(boolean flage,String msg){
		super();
		this.msg = msg;
		this.flage = flage;
		
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isFlage() {
		return flage;
	}

	public void setFlage(boolean flage) {
		this.flage = flage;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "BusinessException [code=" + code + ", flage=" + flage
				+ ", msg=" + msg + "]";
	}

}

	