package com.test.base.entity;

public class Result
{
  private Object object;
  private String msg;
  private String success;

  public Object getObject()
  {
    return this.object;
  }
  public void setObject(Object object) {
    this.object = object;
  }
  public String getMsg() {
    return this.msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public String getSuccess() {
    return this.success;
  }
  public void setSuccess(String success) {
    this.success = success;
  }
}