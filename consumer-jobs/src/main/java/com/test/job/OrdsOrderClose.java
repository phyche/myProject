package com.test.job;

import com.alibaba.fastjson.JSONObject;
import com.test.web.WeconexTaskJob;
import com.test.common.enums.StatusEnum;
import com.test.db.entity.OrdsTest;
import com.test.db.entity.OrdsTestDTO;
import com.test.db.service.OrdsTestService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * 定时器，全部修改名字为test
 *
 * @author lc_xin.
 * @date 2017/10/12
 */
public class OrdsOrderClose implements WeconexTaskJob {
    private static Logger log = LoggerFactory.getLogger(OrdsOrderClose.class);
    @Autowired
    private OrdsTestService ordsTestService;
    @Value("#{configProperties['order.updatetime']}")
    private Integer updateTime;

    public void execute() {
        log.info("收到Quartz的处理");
        System.out.println("================================开始======================================");
        try {
            OrdsTestDTO dto = new OrdsTestDTO();
            List<String> payStatusArr = new ArrayList<>();
            payStatusArr.add(StatusEnum.FAIL.getCode());
            payStatusArr.add(StatusEnum.TO_BE_PAID.getCode());
            /**每次处理100条*/
            List<OrdsTest> ordsTestList = ordsTestService.doQuery(dto, 0, 100);
            log.info("需要修改的数据为：{}", JSONObject.toJSONString(ordsTestList));
            if (CollectionUtils.isNotEmpty(ordsTestList)) {
                for (OrdsTest o : ordsTestList) {
                    o.setTname("test");
                    ordsTestService.doUpdate(o);
                }
            }
            System.out.println("==================================结束====================================");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("处理失败。,{}", e.getMessage());
        }
    }

}
