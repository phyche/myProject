package com.myproject.net;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
* @ClassName: Constant 
* @Description: 系统常亮
* @author cssuger@163.com 
* @date 2016年6月11日 下午7:38:04 
*
 */

public interface Constant {

    int ZK_SESSION_TIMEOUT = 5000;

    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
    
    public static final List<String> IP_LIST = Lists.newArrayList();
}
