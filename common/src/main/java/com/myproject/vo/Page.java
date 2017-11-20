/**
 * 
 */
package com.myproject.vo;

import java.util.List;

/**
 * @author zyl
 * @date 2016年5月26日
 * 
 */
public class Page {
	private int pageNo = 1; // 当前页码，默认第一页
	private int pageSize = 10; // 每页行数，默认每页十行
	private int totalRecord; // 总记录数
	private int totalPage = 1; // 总页数,自动计算
	private List<?> result;//查询出的结果
	public Page(){}
	public Page(int pageNo,int pageSize){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		if(0 == totalPage && 0 != totalRecord){
			totalPage = (totalRecord+pageSize-1)/pageSize;
		}
	}
	public int getTotalPage() {
		if(0 == totalPage && 0 != totalRecord){
			totalPage = (totalRecord+pageSize-1)/pageSize;
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", totalRecord=" + totalRecord + ", totalPage=" + totalPage
				+ ", result=" + result + "]";
	}
	
}
