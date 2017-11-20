package com.test.base.entity;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

public class PageObject
  implements Serializable
{
  private String pageString;
  private RowBounds rowBounds;
  private int currentPage;
  private int totalPage;
  private int perPage;
  private int totalRow;

  public int getCurrentPage()
  {
    return this.currentPage;
  }
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }
  public int getTotalPage() {
    return this.totalPage;
  }
  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }
  public int getPerPage() {
    return this.perPage;
  }
  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }
  public String getPageString() {
    return this.pageString;
  }
  public void setPageString(String pageString) {
    this.pageString = pageString;
  }
  public RowBounds getRowBounds() {
    return this.rowBounds;
  }
  public void setRowBounds(RowBounds rowBounds) {
    this.rowBounds = rowBounds;
  }

  public int getTotalRow()
  {
    return this.totalRow;
  }

  public void setTotalRow(int totalRow)
  {
    this.totalRow = totalRow;
  }
}