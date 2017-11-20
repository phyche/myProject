package com.myproject.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述Java对象与数据库表映射关系的注解类（对应的数据库表名、唯一键类型、唯一键名称）
 * 
 *  @author cssuger@163.com
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableMapperAnnotation {
    /**
     * 
     * @return Dto对应的数据库表的表名
     */
    public String tableName();

    /**
     * 
     * @return 数据库表唯一键类型：UniqueKeyType.Single（单个字段的唯一键）,UniqueKeyType.Union（几个字段组合起来的唯一键）
     */
    public UniqueKeyType uniqueKeyType();

    /**
     * 
     * @return 唯一键名称，多个唯一键用逗号隔开,只能用数据库字段名
     */
    public String uniqueKey();
    
    /**
     * 
     * @Title: dbType 
     * @Description: 数据库类型 
     * @param @return    设定文件 
     * @return String    返回类型 
     * @throws
     */
    public DBType dbType();
}
