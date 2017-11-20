package com.test.common.service.impl;

import com.test.base.entity.ValueObject;
import com.test.base.service.AbstractBaseService;
import com.test.base.service.BaseMapper;
import com.test.db.entity.OrdsTest;
import com.test.db.entity.OrdsTestDTO;
import com.test.db.service.OrdsTestService;
import com.test.common.dao.OrdsTestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by caiqi on 2016/11/23.
 */
@Service("ordsTestService")
public class OrdsTestServiceImpl extends AbstractBaseService<OrdsTest> implements OrdsTestService {
    private Logger logger = LoggerFactory.getLogger(OrdsTestServiceImpl.class);

    @Autowired
    private OrdsTestMapper ordsTestMapper;

    @Override
    protected BaseMapper<OrdsTest> getBaseMapper() {
        return ordsTestMapper;
    }

    @Override
    protected Class<? extends ValueObject> getEntityDTOClass() {
        return OrdsTestDTO.class;
    }
}
