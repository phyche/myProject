package com.test.common.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component("springUtils")
@Lazy(false)
public final class SpringUtils
  implements ApplicationContextAware, DisposableBean
{
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext)
  {
    applicationContext = applicationContext;
  }

  public void destroy() throws Exception {
    applicationContext = null;
  }

  public static ApplicationContext getApplicationContext()
  {
    return applicationContext;
  }

  public static Object getBean(String name)
  {
    Assert.hasText(name);
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(String name, Class<T> type)
  {
    Assert.hasText(name);
    Assert.notNull(type);
    return applicationContext.getBean(name, type);
  }

  public static String getMessage(String code, Object[] args)
  {
    LocaleResolver localeResolver = (LocaleResolver)getBean("localeResolver", LocaleResolver.class);
    Locale locale = localeResolver.resolveLocale(null);
    return applicationContext.getMessage(code, args, locale);
  }
}