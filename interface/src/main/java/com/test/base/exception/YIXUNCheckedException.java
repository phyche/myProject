package com.test.base.exception;

public class YIXUNCheckedException extends Exception
{
  private static final long serialVersionUID = 1531809258214837408L;
  private String code;

  public String getOutMsg()
  {
    YIXUNExceptionCode mcode = YIXUNExceptionCode.getByCode(this.code);
    if (mcode != null) {
      return mcode.getDesout();
    }
    return getMessage();
  }

  public String getInMsg() {
    YIXUNExceptionCode mcode = YIXUNExceptionCode.getByCode(this.code);
    if (mcode != null) {
      return mcode.getDesin();
    }
    return getMessage();
  }

  public YIXUNCheckedException()
  {
  }

  public YIXUNCheckedException(Throwable e) {
    super(e);
  }

  public YIXUNCheckedException(String errorCode)
  {
    this.code = errorCode;
  }

  public YIXUNCheckedException(YIXUNExceptionCode mcode, Throwable e) {
    super(mcode.getDesin(), e);
    this.code = mcode.getCode();
  }

  public YIXUNCheckedException(String code, Throwable e) {
    super(YIXUNExceptionCode.getInMsg(code), e);
    this.code = code;
  }

  public YIXUNCheckedException(String code, String message) {
    super(message);
    this.code = code;
  }

  public YIXUNCheckedException(String code, String message, Throwable e) {
    super(message, e);
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}