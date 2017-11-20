package com.test.base.exception;

public enum YIXUNExceptionCode
{
  UNKNOW_EXCEPTON("9999", "未知异常", "服务器忙"), 

  ILLEGAL_PARAMETER("1000", "参数异常", "参数异常"), 

  MONEY_PARSE_ERROR("3003", "金额转换money类错误", "金额格式错误");

  private String code;
  private String desin;
  private String desout;

  private YIXUNExceptionCode(String code, String desin, String desout) { this.code = code;
    this.desin = desin;
    this.desout = desout;
  }

  public String getCode()
  {
    return this.code;
  }
  public String getDesin() {
    return this.desin;
  }
  public String getDesout() {
    return this.desout;
  }

  public static YIXUNExceptionCode getByCode(String code) {
    if ((code != null) && (!"".equals(code.trim()))) {
      for (YIXUNExceptionCode mnum : values()) {
        if (mnum.getCode().equals(code)) {
          return mnum;
        }
      }
    }
    return null;
  }

  public static String getInMsg(String code) {
    if ((code != null) && (!"".equals(code.trim()))) {
      for (YIXUNExceptionCode mnum : values()) {
        if (mnum.getCode().equals(code)) {
          return mnum.getDesin();
        }
      }
    }
    return null;
  }

  public static String getOutMsg(String code) {
    if ((code != null) && (!"".equals(code.trim()))) {
      for (YIXUNExceptionCode mnum : values()) {
        if (mnum.getCode().equals(code)) {
          return mnum.getDesout();
        }
      }
    }
    return null;
  }
}