package com.test.base.entity;

import java.io.Serializable;
import java.util.List;

public class Page<T>
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private long total = 0L;

  private List<T> rows = null;

  private long totalPages = 0L;

  private int pageNumber = 0;

  private int pageSize = 0;
  private String errorMsg;

  public String getErrorMsg()
  {
    return this.errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public void setTotalPages(long totalPages) {
    this.totalPages = totalPages;
  }

  public Page()
  {
  }

  public Page(long total, List<T> rows)
  {
    this.total = total;
    this.rows = rows;
  }

  public long getTotal() {
    return this.total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public List<T> getRows() {
    return this.rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  public long getTotalPages() {
    return this.totalPages;
  }

  public int getPageNumber() {
    return this.pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
    if (this.total % pageSize != 0L)
      this.totalPages = (this.total / pageSize + 1L);
    else
      this.totalPages = (this.total / pageSize);
  }
}