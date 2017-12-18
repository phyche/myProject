package com.test.handler.myProject;


import com.test.controller.base.ApiMessageException;
import com.test.db.entity.OrdsTest;
import com.test.db.entity.OrdsTestDTO;
import com.test.db.service.OrdsTestService;
import com.test.entity.ResponeResultVO;
import com.test.entity.ResponeVO;
import com.test.handler.AbstractApiHandler;
import com.test.handler.ApiHandlerCallback;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 测试接口
 * @author lc_xin.
 * @date 2017.08.04
 */
@Service
public class OrderTestHandler extends AbstractApiHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderTestHandler.class);

    @Autowired
    private OrdsTestService ordsTestService;
    /**
     * 图片上传请求路径
     */
    @Value("#{configProperties['requestPath']}")
    public String  imageRequestPathStr;

    @Override
    public ResponeVO doHandler(Object params) {
        final OrderTestParams sparams = (OrderTestParams) params;
        return super.buildCallback(new ApiHandlerCallback() {
            @Override
            public ResponeVO callback() throws Exception {

                OrdsTestDTO dto = new OrdsTestDTO();
                dto.setTname(sparams.getTname());
                List<OrdsTest> ordsTestList = ordsTestService.doQuery(dto,0,Integer.MAX_VALUE);
                logger.info("lyk||OrderTestHandler||返回的参数为：" + ordsTestList);
                return new ResponeResultVO(ordsTestList);
            }
        });
    }

    @Override
    public void checkRequestParams(Object params) throws ApiMessageException {
        OrderTestParams sparams = (OrderTestParams) params;
        logger.info("lyk||激活卡券接口reqParam：" + this.getClass().getSimpleName() + "==========>>" + sparams.toString());
        if(isBlank(sparams.getTname())){
            throw new ApiMessageException(RESP_PARAMS_ERROR);
        }

    }

    @Override
    public Class<?> getRequestParams() {
        return OrderTestParams.class;
    }


    public static class OrderTestParams {
        private String tname;//名字

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        @Override
        public String toString() {
            return "OrderTestParams{" +
                    "tname='" + tname + '\'' +
                    '}';
        }
    }
}
