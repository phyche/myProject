package com.myproject.interceptor.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.myproject.interceptor.annotation.DBType;
import com.myproject.interceptor.annotation.TableMapper;
import com.myproject.interceptor.annotation.TableMapperAnnotation;
import com.myproject.interceptor.annotation.UniqueKeyType;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.type.JdbcType;

import com.myproject.interceptor.annotation.FieldMapper;
import com.myproject.interceptor.annotation.FieldMapperAnnotation;
import com.myproject.interceptor.annotation.SequenceGenerator;
import com.myproject.util.StringTools;





/**
 * 
* @ClassName: SqlBuilder 
* @Description: 根据注解，自动生成sql语句 
* @author cssuger@163.com 
* @date 2016年6月27日 上午10:47:18 
*
 */

public class SqlBuilder {
    /**
     * 缓存TableMapper
     */
    private static HashMap<Class<?>, TableMapper> tableMapperCache = new HashMap<Class<?>, TableMapper>(128);

    /**
     * 由传入的dto对象的class构建TableMapper对象，构建好的对象存入缓存中，以后使用时直接从缓存中获取
     * 
     * @param dtoClass
     * @return TableMapper
     */
    private static TableMapper buildTableMapper(Class<?> dtoClass) {

        HashMap<String, FieldMapper> fieldMapperCache = null;
        ArrayList<FieldMapper> fieldMapperList = null;
        Field[] fields = null;

        FieldMapperAnnotation fieldMapperAnnotation = null;
        FieldMapper fieldMapper = null;
        TableMapper tableMapper = null;
       
        synchronized (tableMapperCache) {
            tableMapper = tableMapperCache.get(dtoClass);
            if (tableMapper != null) {
                return tableMapper;
            }
            tableMapper = new TableMapper();
            Annotation[] classAnnotations = dtoClass.getDeclaredAnnotations();
            if (classAnnotations.length == 0) {
                throw new RuntimeException("Class " + dtoClass.getName()
                        + " has no annotation, I can't build 'TableMapper' for it.");
            }
            for (Annotation an : classAnnotations) {
                if (an instanceof TableMapperAnnotation) {
                    tableMapper.setTableMapperAnnotation(an);
                }
            }
            if (tableMapper.getTableMapperAnnotation() == null) {
                throw new RuntimeException("Class " + dtoClass.getName() + " has no 'TableMapperAnnotation', "
                        + "which has the database table information," + " I can't build 'TableMapper' for it.");
            }
            fields = dtoClass.getDeclaredFields();
            fieldMapperCache = new HashMap<String, FieldMapper>();
            fieldMapperList = new ArrayList<FieldMapper>();
            //Annotation[] fieldAnnotations = null;
            for (Field field : fields) {
//                fieldAnnotations = field.getDeclaredAnnotations();
//                if (fieldAnnotations.length == 0) {
//                    continue;
//                }
                fieldMapperAnnotation = field.getAnnotation(FieldMapperAnnotation.class);
                //防止有serialVersionUID这个的未null
                if(null == fieldMapperAnnotation){
                	continue;
                }
                SequenceGenerator sequencegenerator = field.getAnnotation(SequenceGenerator.class);
                fieldMapper = new FieldMapper();
                fieldMapper.setFieldName(field.getName());
                fieldMapper.setDbFieldName(fieldMapperAnnotation.dbFieldName());
                fieldMapper.setJdbcType(fieldMapperAnnotation.jdbcType());
                if(null != sequencegenerator){
                	fieldMapper.setSeqName(sequencegenerator.sequenceName());
                }else{
                	fieldMapper.setSeqName(null);
                }
                
                fieldMapperCache.put(fieldMapperAnnotation.dbFieldName(), fieldMapper);
                fieldMapperList.add(fieldMapper);
//                for (Annotation an : fieldAnnotations) {
//                    if (an instanceof FieldMapperAnnotation) {
//                        fieldMapperAnnotation = (FieldMapperAnnotation) an;
//                        fieldMapper = new FieldMapper();
//                        fieldMapper.setFieldName(field.getName());
//                        fieldMapper.setDbFieldName(fieldMapperAnnotation.dbFieldName());
//                        fieldMapper.setJdbcType(fieldMapperAnnotation.jdbcType());
//                        fieldMapperCache.put(fieldMapperAnnotation.dbFieldName(), fieldMapper);
//                        fieldMapperList.add(fieldMapper);
//                    }
//                    if(an instanceof SequenceGenerator){
//                    	SequenceGenerator seq = (SequenceGenerator)an;
//                    	 String sequenceName  = seq.sequenceName();
//                    
//                    }
//                }
            }
            tableMapper.setFieldMapperCache(fieldMapperCache);
            tableMapper.setFieldMapperList(fieldMapperList);
           
            tableMapperCache.put(dtoClass, tableMapper);
            return tableMapper;
        }
    }

    /**
     * 从注解里获取唯一键信息
     * 
     * @param tableMapper
     * @return
     */
    private static String[] buildUniqueKey(TableMapper tableMapper) {
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String[] uniqueKeyNames = null;
        if (tma.uniqueKeyType().equals(UniqueKeyType.Single)) {
            uniqueKeyNames = new String[1];
            uniqueKeyNames[0] = tma.uniqueKey();
        } else {
            uniqueKeyNames = tma.uniqueKey().split(",");
        }
        return uniqueKeyNames;
    }

    /**
     * 由传入的对象生成insert sql语句
     * 
     * @param tableMapper
     * @param dto
     * @return sql
     * @throws Exception
     */
    public static String buildInsertSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        // 从参数对象里提取注解信息
        TableMapper tableMapper = buildTableMapper(object.getClass());
        // 从表注解里获取表名等信息
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String dbType = tma.dbType().name();//数据库类型
        StringBuffer tableSql = new StringBuffer();
        StringBuffer valueSql = new StringBuffer();
        
        tableSql.append("insert into ").append(tableName).append("(");
        valueSql.append("values(");

        boolean allFieldNull = true;
        // 根据字段注解和属性值联合生成sql语句
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
        	//String sequenceName = tableMapper.getSequenceMapper().getSequenceName();//如果是oracle的话，得到sequenceName名称
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(dbFieldName);
            String sequenceName = fieldMapper.getSeqName();//如果是oracle的话，得到sequenceName名称
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            // 由于要根据字段对象值是否为空来判断是否将字段加入到sql语句中，因此DTO对象的属性不能是简单类型，反而必须是封装类型
//            if (value == null) {
//                continue;
//            }
            if(!StringTools.isBlank(sequenceName)){
            	tableSql.append(dbFieldName).append(",");
            	valueSql.append(sequenceName+".nextval").append(",");
            }else{
            	tableSql.append(dbFieldName).append(",");
            	//如果是DATE类型的
            	if(fieldMapper.getJdbcType().equals(JdbcType.DATE) && value != null){
            		buliderDateByDB(valueSql,dbType,value);
            		 //valueSql.append("to_date('"+value+"','yyyy-mm-dd HH24:mi:ss') ").append(",");
            	}else {
            		//if(!fieldMapper.getJdbcType().equals(JdbcType.DATE))
            		 valueSql.append("#{").append(fieldName).append(",").append("jdbcType=")
                     .append(fieldMapper.getJdbcType().toString()).append("},");
            	}
            	
           	
            }
            allFieldNull = false;
            
           
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName()
                    + "'s all fields are null, how can i build sql for it?!");
        }
        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        valueSql.delete(valueSql.lastIndexOf(","), valueSql.lastIndexOf(",") + 1);
        return tableSql.append(") ").append(valueSql).append(")").toString();
    }
    
    /**
     * 
     * @Title: buliderDateByDB 
     * @Description: 构建日期函数
     * @param @param valueSql
     * @param @param dbtype
     * @param @param value
     * @param @return    设定文件 
     * @return StringBuffer    返回类型 
     * @throws
     */
    private static void buliderDateByDB(StringBuffer valueSql,String dbtype,Object value){
    	if(dbtype.equals(DBType.MYSQL.name())){
    		 valueSql.append("STR_TO_DATE('"+value+"','%Y-%m-%d %H:%i:%s') ").append(",");
    	}else if(dbtype.equals(DBType.ORACLE.name())){
    		valueSql.append("to_date('"+value+"','yyyy-mm-dd HH24:mi:ss') ").append(",");
    	}else if(dbtype.equals(DBType.MSSQL.name())){
    		
    	}else {
    		throw new IllegalAccessError("不合法的sql");
    	}
    }

    /**
     * 由传入的对象生成update sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildUpdateSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String dbType = tma.dbType().name();//数据库类型
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);
        String uniqueKey = tma.uniqueKey();//该表的主键
        StringBuffer tableSql = new StringBuffer();
        StringBuffer whereSql = new StringBuffer(" where ");

        tableSql.append("update ").append(tableName).append(" set ");

        boolean allFieldNull = true;

        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(dbFieldName);
            String fieldName = fieldMapper.getFieldName();
            
            Object value = dtoFieldMap.get(fieldName);
//            if (value == null) {
//                continue;
//            }
            allFieldNull = false;
            //如果不是主键
            if(!uniqueKey.equals(dbFieldName)){
            	if(fieldMapper.getJdbcType().equals(JdbcType.DATE) && value != null){
            		//buliderDateByDB(tableSql,dbType,value);
            		buliderUpdateSqlByDbtype(tableSql,dbType,value,dbFieldName);
            		 //valueSql.append("to_date('"+value+"','yyyy-mm-dd HH24:mi:ss') ").append(",");
            		
            	}else {
            		tableSql.append(dbFieldName).append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("},");
            	}
            	
            }
            
        }
        if (allFieldNull) {
            throw new RuntimeException("Are you joking? Object " + object.getClass().getName()
                    + "'s all fields are null, how can i build sql for it?!");
        }

        tableSql.delete(tableSql.lastIndexOf(","), tableSql.lastIndexOf(",") + 1);
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            whereSql.append(uniqueKeyNames[i]);
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
                        + "' can't be null, build update sql failed!");
            }
            whereSql.append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        whereSql.delete(whereSql.lastIndexOf("and"), whereSql.lastIndexOf("and") + 3);
        return tableSql.append(whereSql).toString();
    }

    /**
     * 
     * @Title: buliderUpdateSqlByDbtype 
     * @Description: 根据db类型去构建date函数 
     * @param @param valueSql
     * @param @param dbtype
     * @param @param value    设定文件 
     * @return void    返回类型 
     * @throws
     */
	private static void buliderUpdateSqlByDbtype(StringBuffer tableSql,String dbtype, Object value,String dbFieldName) {
		if (dbtype.equals(DBType.MYSQL.name())) {
			tableSql.append(dbFieldName).append("=").append("STR_TO_DATE('" + value + "','%Y-%m-%d %H:%i:%s') ").append(",");
		} else if (dbtype.equals(DBType.ORACLE.name())) {
			tableSql.append(dbFieldName).append("=").append("to_date('" + value + "','yyyy-mm-dd HH24:mi:ss') ").append(",");
		} else if (dbtype.equals(DBType.MSSQL.name())) {

		} else {
			throw new IllegalAccessError("不合法的sql");
		}
	}
    
    /**
     * 由传入的对象生成update sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildDeleteSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);

        StringBuffer sql = new StringBuffer();

        // delete from tableName where primaryKeyName=?
        sql.append("delete from ").append(tableName).append(" where ");
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            sql.append(uniqueKeyNames[i]);
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
                        + "' can't be null, build update sql failed!");
            }
            sql.append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        sql.delete(sql.lastIndexOf("and"), sql.lastIndexOf("and") + 3);

        return sql.toString();
    }

    /**
     * 由传入的对象生成query sql语句
     * 
     * @param object
     * @return sql
     * @throws Exception
     */
    public static String buildSelectSql(Object object) throws Exception {
        if (null == object) {
            throw new RuntimeException("Sorry,I refuse to build sql for a null object!");
        }
        Map dtoFieldMap = PropertyUtils.describe(object);
        TableMapper tableMapper = buildTableMapper(object.getClass());
        TableMapperAnnotation tma = (TableMapperAnnotation) tableMapper.getTableMapperAnnotation();
        String tableName = tma.tableName();
        String[] uniqueKeyNames = buildUniqueKey(tableMapper);

        StringBuffer selectSql = new StringBuffer();
        selectSql.append("select ");
        for (String dbFieldName : tableMapper.getFieldMapperCache().keySet()) {
            selectSql.append(dbFieldName).append(",");
        }
        selectSql.delete(selectSql.lastIndexOf(","), selectSql.lastIndexOf(",") + 3);
        selectSql.append(" from ").append(tableName);

        StringBuffer whereSql = new StringBuffer(" where ");
        for (int i = 0; i < uniqueKeyNames.length; i++) {
            whereSql.append(uniqueKeyNames[i]);
            FieldMapper fieldMapper = tableMapper.getFieldMapperCache().get(uniqueKeyNames[i]);
            String fieldName = fieldMapper.getFieldName();
            Object value = dtoFieldMap.get(fieldName);
            if (value == null) {
                throw new RuntimeException("Unique key '" + uniqueKeyNames[i]
                        + "' can't be null, build query sql failed!");
            }
            whereSql.append("=#{").append(fieldName).append(",").append("jdbcType=")
                    .append(fieldMapper.getJdbcType().toString()).append("} and ");
        }
        whereSql.delete(whereSql.lastIndexOf("and"), whereSql.lastIndexOf("and") + 3);
        return selectSql.append(whereSql).toString();
    }

}
