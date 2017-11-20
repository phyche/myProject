/**   
* @Title: JqueryDataGridVo.java 
* @Package com.cyber.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月8日 上午9:23:28 
* @version V1.0   
*/
package com.myproject.vo;

import java.util.List;

/** 
 * @ClassName: JqueryDataGridVo 
 * @Description: Jqueryeasuiyui里面的datagrid数据结构
 * 它的json格式如下:
 * {"total":28,"rows":[  
{"产品ID":"FI-SW-01","产品名称":"Koi","价格":10.00,"状态":"P"},  
{"产品ID":"K9-DL-01","产品名称":"Dalmation","价格":12.00,"状态"":"P"},  
{"产品ID":"RP-SN-01","产品名称":"Rattlesnake","价格":12.00,"状态"":"P"},  
{"产品ID":"RP-SN-01","产品名称":"Rattlesnake","价格":12.00,"状态"":"P"},  
{"产品ID":"RP-LI-02","产品名称":"Iguana","价格":12.00,"状态"":"P"},  
{"产品ID":"FL-DSH-01","产品名称":"Manx","价格":12.00,"状态"":"P"},  
]}  
 * @author cssuger@163.com 
 * @date 2016年6月8日 上午9:23:28 
 *  
 */
public class JqueryDataGridVo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7698858216171543392L;
	
	/**
	 * 总条数
	 */
	private long total;
	
	//每页显示的数据大小的集合
	private List<?> rows;

	
	public JqueryDataGridVo(){
		
	}
	
    public JqueryDataGridVo(Page page){
    	this.rows = page.getResult();
    	this.total = page.getTotalRecord();
	}
	
    public JqueryDataGridVo(long total,List<?> rows){
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "JqueryDataGridVo [total=" + total + ", list=" + rows + "]";
	}
    
    
}
