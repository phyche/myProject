package com.test.base.exception;

public class TransferProcessingException extends YIXUNCheckedException
{
  private static final long serialVersionUID = -4334525392534213980L;
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

  public TransferProcessingException()
  {
  }

  public TransferProcessingException(Throwable e) {
    super(e);
  }

  public TransferProcessingException(String errorCode)
  {
    this.code = errorCode;
  }

  public TransferProcessingException(YIXUNExceptionCode mcode, Throwable e) {
    super(mcode.getDesin(), e);
    this.code = mcode.getCode();
  }

  public TransferProcessingException(String code, Throwable e) {
    super(YIXUNExceptionCode.getInMsg(code), e);
    this.code = code;
  }

  public TransferProcessingException(String code, String message) {
    super(message);
    this.code = code;
  }

  public TransferProcessingException(String code, String message, Throwable e) {
    super(message, e);
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}