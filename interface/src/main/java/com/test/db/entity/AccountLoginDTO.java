package com.test.db.entity;


import com.test.base.entity.ValueObjectDTO;
import com.test.common.utils.StringUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lc_xin.
 * @date 2016年6月12日
 */
public class AccountLoginDTO extends AccountLogin implements ValueObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -469502201613326098L;
	private String creationtime;        //页面传过来的时间  是创建时间
	private String creationendtime;    //页面传过来的创建时间的范围值 当天的最后一秒

	private String createdBegin;
	private String createdEnd;

	private String cookie;
	private List<String> reqBizTypeList;
	private String umengdevId;

	public String getCreatedBegin() {
		return createdBegin;
	}

	public void setCreatedBegin(String createdBegin) {
		this.createdBegin = createdBegin;
	}

	public String getCreatedEnd() {
		return createdEnd;
	}

	public void setCreatedEnd(String createdEnd) {
		this.createdEnd = createdEnd;
	}

	public String getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(String creationtime) {
		this.creationtime = creationtime;
	}

	public String getCreationendtime() {
		if(!StringUtil.isEmpty(this.getCreationtime())) {
			return this.getCreationtime() + " 23:59:59";
		}
		return  creationendtime;
	}

	public void setCreationendtime(String creationendtime) {
		this.creationendtime = creationendtime;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public List<String> getReqBizTypeList() {
		return reqBizTypeList;
	}

	public void setReqBizTypeList(List<String> reqBizTypeList) {
		this.reqBizTypeList = reqBizTypeList;
	}

	public String getUmengdevId() {
		return umengdevId;
	}

	public void setUmengdevId(String umengdevId) {
		this.umengdevId = umengdevId;
	}

	/**
	 *需要正序字段名
	 */
	private List<String> asc;
	/**
	 *需要逆序字段名
	 */
	private List<String> desc;
	/**
	 *排序SQL
	 */
	private String orders;

	/**
	 *  添加需要正序字段名
	 */
	public void addAsc(String col){
		if(asc==null){
			asc = new LinkedList<String>();
		}
		asc.add(col);
	}

	/**
	 *  清空需要正序字段名
	 */
	public void cleanAsc(){
		asc = null;
	}

	/**
	 *  添加需要逆序字段名
	 */
	public void addDesc(String col){
		if(desc==null){
			desc = new LinkedList<String>();
		}
		desc.add(col);
	}

	/**
	 *  清空需要逆序字段名
	 */
	public void cleanDesc(){
		desc = null;
	}

	/**
	 *  如果排序SQL为空根据需要正逆序的字段名拼接排序SQL
	 */
	public String  getOrders() {
		StringBuilder orderStr = null;
		StringBuilder ascStr;
		StringBuilder descStr;
		if(orders==null){
			if(asc!=null){
				ascStr = new StringBuilder();
				orderStr = new StringBuilder();
				String pex = "";
				for(String a : asc){
					ascStr.append(pex+a);
					pex = ",";
				}
				orderStr.append(ascStr.toString()+" ASC");
			}
			if(desc!=null){
				descStr = new StringBuilder();
				String pex = "";
				for(String d : desc){
					descStr.append(pex+d);
					pex = ",";
				}
				if(orderStr==null){
					orderStr = new StringBuilder();
					orderStr.append(descStr.toString()+" DESC");
				}else{
					orderStr.append(","+descStr.toString()+" DESC");
				}
			}
			orders = orderStr!=null?orderStr.toString():null;
		}

		return orders;
	}

	/**
	 *  设置排序SQL
	 */
	public void setOrders(String orders) {
		this.orders = orders;
	}
}
