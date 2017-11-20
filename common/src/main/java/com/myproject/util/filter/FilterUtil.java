/**   
* @Title: FilterUtil.java 
* @Package com.cyber.util.filter 
* @Description: TODO(用一句话描述该文件做什么) 
* @author cssuger@163.com   
* @date 2016年6月16日 上午11:16:57 
* @version V1.0   
*/
package com.myproject.util.filter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/** 
 * @ClassName: FilterUtil 
 * @Description: 基于集合(Map,List)内存数据过滤，
 * @author cssuger@163.com 
 * @date 2016年6月16日 上午11:16:57 
 *  
 */
public class FilterUtil {

	/**
	 * 
	 * @Title: filterValues 
	 * @Description: 结合list过滤 
	 * @param @param list 要过滤的集合
	 * @param @param predicate 过滤条件借口，该接口需要自己实现
	 * @param @return    设定文件 
	 * @return List<T>    返回类型 
	 * @throws
	 */
	public static <T>List<T> filterValues(List<T> list,Predicate<? super T> predicate){
		 Collection<T> collection = Collections2.filter(list, predicate);
		 return Lists.newArrayList(collection);
	}
	
	/**
	 * 
	 * @Title: filterValues 
	 * @Description: 结合set过滤 
	 * @param @param set 要过滤的集合
	 * @param @param predicate 过滤条件借口，该接口需要自己实现
	 * @param @return    设定文件 
	 * @return List<T>    返回类型 
	 * @throws
	 */
	public static <T>Set<T> filterValues(Set<T> list,Predicate<? super T> predicate){
		 Collection<T> collection = Collections2.filter(list, predicate);
		 return Sets.newHashSet(collection);
	}
	
	/**
	 * 
	 * @Title: filterKeys 
	 * @Description: 集合map key过滤 
	 * @param @param map
	 * @param @param predicate 过滤条件借口，该接口需要自己实现
	 * @param @return    设定文件 
	 * @return Map<K,V>    返回类型 
	 * @throws
	 */
	public static <K,V>Map<K,V> filterKeys(Map<K,V> map ,Predicate<? super K> predicate){
		return Maps.filterKeys(map, predicate);
	}
	
	/**
	 * 
	 * @Title: filterKeys 
	 * @Description: 集合map key过滤 
	 * @param @param map
	 * @param @param predicate 过滤条件借口，该接口需要自己实现
	 * @param @return    设定文件 
	 * @return Map<K,V>    返回类型 
	 * @throws
	 */
	public static <K,V>Map<K,V> filterValues(Map<K,V> map ,Predicate<? super V> predicate){
		return Maps.filterValues(map, predicate);
	}
}
