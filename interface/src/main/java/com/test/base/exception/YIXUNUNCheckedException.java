package com.test.base.exception;

public class YIXUNUNCheckedException extends RuntimeException
{
  private static final long serialVersionUID = 1720755012635650684L;
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

  public YIXUNUNCheckedException()
  {
  }

  public YIXUNUNCheckedException(Throwable e) {
    super(e);
  }

  public YIXUNUNCheckedException(String errorCode)
  {
    this.code = errorCode;
  }

  public YIXUNUNCheckedException(YIXUNExceptionCode mcode, Throwable e) {
    super(mcode.getDesin(), e);
    this.code = mcode.getCode();
  }

  public YIXUNUNCheckedException(String code, Throwable e) {
    super(YIXUNExceptionCode.getInMsg(code), e);
    this.code = code;
  }

  public YIXUNUNCheckedException(String code, String message) {
    super(message);
    this.code = code;
  }

  public YIXUNUNCheckedException(String code, String message, Throwable e) {
    super(message, e);
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}