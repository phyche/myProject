package com.test.common.utils;

public class StringUtil
{
  public static String nvl(String value)
  {
    if (value == null) {
      return "";
    }
    return value.trim();
  }

  public static String nvlNull(String value)
  {
    if ((value == null) || ("".equals(value.trim()))) {
      return null;
    }
    return value.trim();
  }

  public static boolean isEqual(String s1, String s2)
  {
    if ((s1 == null) && (s2 == null))
    {
      return true;
    }
    if (s1 == null) {
      return false;
    }
    if (s2 == null) {
      return false;
    }
    return s1.equals(s2);
  }

  public static boolean isEmpty(String value)
  {
    if ((value == null) || ("".equals(value.trim()))) {
      return true;
    }
    return false;
  }

  public static String getMemberBankAcctAlias(String memberBankAcct)
  {
    int l = memberBankAcct.length();
    return "********" + memberBankAcct.substring(l - 4);
  }
}