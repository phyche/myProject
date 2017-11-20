package com.test.common.utils;

import com.test.base.entity.CommonAttributes;
import com.test.common.entity.EnumConverter;
import freemarker.core.Environment;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public final class FreemarkerUtils
{
  private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtils.class);

  private static final ConvertUtilsBean convertUtils = new ConvertUtilsBean()
  {
    public String convert(Object value) {
      if (value != null) {
        Class type = value.getClass();
        if ((type.isEnum()) && (super.lookup(type) == null)) {
          super.register(new EnumConverter(type), type);
        } else if ((type.isArray()) && (type.getComponentType().isEnum())) {
          if (super.lookup(type) == null) {
            ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
            arrayConverter.setOnlyFirstToString(false);
            super.register(arrayConverter, type);
          }
          Converter converter = super.lookup(type);
          return (String)converter.convert(String.class, value);
        }
      }
      return super.convert(value);
    }

    public Object convert(String value, Class clazz)
    {
      if ((clazz.isEnum()) && (super.lookup(clazz) == null)) {
        super.register(new EnumConverter(clazz), clazz);
      }
      return super.convert(value, clazz);
    }

    public Object convert(String[] values, Class clazz)
    {
      if ((clazz.isArray()) && (clazz.getComponentType().isEnum()) && (super.lookup(clazz.getComponentType()) == null)) {
        super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
      }
      return super.convert(values, clazz);
    }

    public Object convert(Object value, Class targetType)
    {
      if (super.lookup(targetType) == null) {
        if (targetType.isEnum()) {
          super.register(new EnumConverter(targetType), targetType);
        } else if ((targetType.isArray()) && (targetType.getComponentType().isEnum())) {
          ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
          arrayConverter.setOnlyFirstToString(false);
          super.register(arrayConverter, targetType);
        }
      }
      return super.convert(value, targetType);
    }
  };

  public static String process(String template, Map<String, ?> model)
    throws IOException, TemplateException
  {
    Configuration configuration = null;
    ApplicationContext applicationContext = SpringUtils.getApplicationContext();
    if (applicationContext != null) {
      FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer)SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
      if (freeMarkerConfigurer != null) {
        configuration = freeMarkerConfigurer.getConfiguration();
      }
    }
    return process(template, model, configuration);
  }

  public static String process(String template, Map<String, ?> model, Configuration configuration)
    throws IOException, TemplateException
  {
    if (template == null) {
      return null;
    }
    if (configuration == null) {
      configuration = new Configuration();
    }
    StringWriter out = new StringWriter();
    new Template("template", new StringReader(template), configuration).process(model, out);
    return out.toString();
  }

  public static <T> T getParameter(String name, Class<T> type, Map<String, TemplateModel> params)
    throws TemplateModelException
  {
    Assert.hasText(name);
    Assert.notNull(type);
    Assert.notNull(params);
    TemplateModel templateModel = (TemplateModel)params.get(name);
    if (templateModel == null) {
      return null;
    }
    Object value = DeepUnwrap.unwrap(templateModel);
    return (T)convertUtils.convert(value, type);
  }

  public static TemplateModel getVariable(String name, Environment env)
    throws TemplateModelException
  {
    Assert.hasText(name);
    Assert.notNull(env);
    return env.getVariable(name);
  }

  public static void setVariable(String name, Object value, Environment env)
    throws TemplateException
  {
    Assert.hasText(name);
    Assert.notNull(env);
    if ((value instanceof TemplateModel))
      env.setVariable(name, (TemplateModel)value);
    else
      env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
  }

  public static void setVariables(Map<String, Object> variables, Environment env)
    throws TemplateException
  {
    Assert.notNull(variables);
    Assert.notNull(env);
    for (Entry entry : variables.entrySet()) {
      String name = (String)entry.getKey();
      Object value = entry.getValue();
      if ((value instanceof TemplateModel))
        env.setVariable(name, (TemplateModel)value);
      else
        env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
    }
  }

  public static String process(File template, Map<String, Object> rootMap)
  {
    if ((template == null) || (!template.exists())) {
      logger.info("文件不存在");
      return null;
    }
    try {
      FileInputStream in = new FileInputStream(template);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      Template tmp = new Template("template", reader, null);
      StringWriter out = new StringWriter();
      tmp.process(rootMap, out);
      out.close();
      reader.close();
      in.close();
      return out.toString();
    } catch (IOException e) {
      logger.error("模板读取出现异常", e);
    } catch (Exception e) {
      logger.error("模板读取出现异常", e);
    }
    return null;
  }

  static
  {
    DateConverter dateConverter = new DateConverter();
    dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
    convertUtils.register(dateConverter, Date.class);
  }
}