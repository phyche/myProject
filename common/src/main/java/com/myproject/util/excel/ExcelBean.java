package com.myproject.util.excel;

import java.util.List;

public class ExcelBean
{
  private String title;
  private String creator;
  private List list;
  private String[] hearders;
  private String[][] children;
  private String[] fields;

  public ExcelBean()
  {
  }

  public ExcelBean(String title, String creator, List list, String[] hearders)
  {
    this.title = title;
    this.creator = creator;
    this.list = list;
    this.hearders = hearders;
  }

  public ExcelBean(String title, String creator, List list, String[] hearders, String[] fields)
  {
    this.title = title;
    this.creator = creator;
    this.list = list;
    this.hearders = hearders;
    this.fields = fields;
  }

  public ExcelBean(String title, String creator, List list, String[] hearders, String[][] children, String[] fields)
  {
    this.title = title;
    this.creator = creator;
    this.list = list;
    this.hearders = hearders;
    this.children = children;
    this.fields = fields;
  }

  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getCreator() {
    return this.creator;
  }
  public void setCreator(String creator) {
    this.creator = creator;
  }
  public List getList() {
    return this.list;
  }
  public void setList(List list) {
    this.list = list;
  }
  public String[] getHearders() {
    return this.hearders;
  }
  public void setHearders(String[] hearders) {
    this.hearders = hearders;
  }
  public String[][] getChildren() {
    return this.children;
  }
  public void setChildren(String[][] children) {
    this.children = children;
  }
  public String[] getFields() {
    return this.fields;
  }
  public void setFields(String[] fields) {
    this.fields = fields;
  }
}