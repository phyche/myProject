package com.test.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResultList implements Serializable{
	
	private static final long serialVersionUID = -4929312154798843488L;
	private int total;
	private int totalPages;
	private int pageSize;
	private int pageNumber;
	private List<Map<String, Object>> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}
}
