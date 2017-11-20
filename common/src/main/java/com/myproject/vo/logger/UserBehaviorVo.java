/**   
 * @Title: UserBehaviorVo.java 
 * @Package com.cyber.vo.log 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author cssuger@163.com   
 * @date 2016年5月25日 下午6:37:01 
 * @version V1.0   
 */
package com.myproject.vo.logger;

import java.io.Serializable;

/**
 * @ClassName: UserBehaviorVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cssuger@163.com
 * @date 2016年5月25日 下午6:37:01
 * 
 */
public class UserBehaviorVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7626961559932076245L;
	private Integer id;
	private String begindate;
	private String remoteip;
	private String remoteuser;
	private String url;
	private String querystring;
	private String method;
	private String classname;
	private String parammap;
	private String enddate;
	

	

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBegindate() {
		return this.begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = (begindate == null ? null : begindate.trim());
	}

	public String getRemoteip() {
		return this.remoteip;
	}

	public void setRemoteip(String remoteip) {
		this.remoteip = (remoteip == null ? null : remoteip.trim());
	}

	public String getRemoteuser() {
		return this.remoteuser;
	}

	public void setRemoteuser(String remoteuser) {
		this.remoteuser = (remoteuser == null ? null : remoteuser.trim());
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = (url == null ? null : url.trim());
	}

	public String getQuerystring() {
		return this.querystring;
	}

	public void setQuerystring(String querystring) {
		this.querystring = (querystring == null ? null : querystring.trim());
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = (method == null ? null : method.trim());
	}

	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = (classname == null ? null : classname.trim());
	}

	public String getParammap() {
		return this.parammap;
	}

	public void setParammap(String parammap) {
		this.parammap = (parammap == null ? null : parammap.trim());
	}

	@Override
	public String toString() {
		return "UserBehaviorVo [id=" + id + ", begindate=" + begindate
				+ ", remoteip=" + remoteip + ", remoteuser=" + remoteuser
				+ ", url=" + url + ", querystring=" + querystring + ", method="
				+ method + ", classname=" + classname + ", parammap="
				+ parammap + ", enddate=" + enddate + "]";
	}
}
