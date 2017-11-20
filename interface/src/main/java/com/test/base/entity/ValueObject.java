package com.test.base.entity;

import java.io.Serializable;
import java.util.Date;

public class ValueObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6735559581657875452L;
	/**ID*/
	protected Integer id;
	/**创建时间*/
	protected Date created;
	/**版本*/
	protected int version;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
