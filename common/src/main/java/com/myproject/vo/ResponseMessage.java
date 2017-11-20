/**
 * 
 */
package com.myproject.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
* @Title: ResponseMessage.java 
* @Package cn.com.jsoup.util.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2014年12月20日 上午8:51:24 
* @version V1.0
 * @param <T>
 */
@XmlRootElement(name="responsemessage")
public class ResponseMessage<T> implements java.io.Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6082737145802744423L;

    protected boolean state;

    protected String code;

    protected String tip;

    protected List<T> list;

    protected T t;
    

    public ResponseMessage() {
       
    }
    
    public ResponseMessage(boolean state, String code, String tip) {
        this.state = state;
        this.code = code;
        this.tip = tip;
    }
    
    public ResponseMessage(boolean state, T t,  String tip) {
        this.state = state;
        this.t = t;
        this.tip = tip;
    }

    public ResponseMessage(boolean state, List<T> list) {
        this.state = state;
        this.list = list;
    }

   

    public ResponseMessage(boolean state, String tip) {
        this.state = state;
        this.tip = tip;

    }
    
    @XmlElement(name="state")
    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
    @XmlElement(name="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlElement(name="tip")
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

   

    @SuppressWarnings("rawtypes")
    @XmlElement(name="list")
	public List getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

   
    @XmlElement(name="t")
    public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "ResponseMessage [state=" + state + ", code=" + code + ", tip="
				+ tip + ", list=" + list + ", t=" + t
				+ "]";
	}

}
