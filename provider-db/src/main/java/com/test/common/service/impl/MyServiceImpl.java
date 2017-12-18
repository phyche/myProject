package com.test.common.service.impl;


import com.test.common.dao.MyMapper;
import com.test.db.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */
@Service("myService")
public class MyServiceImpl implements MyService {

    @Autowired
    private MyMapper myMapper;

    public List query(String sql){
        List a = myMapper.queryBySql(sql);
        return a;
    }
}
