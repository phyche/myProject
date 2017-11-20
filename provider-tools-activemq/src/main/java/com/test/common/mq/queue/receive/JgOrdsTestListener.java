package com.test.common.mq.queue.receive;

import com.alibaba.fastjson.JSONObject;
import com.myproject.util.StringUtil;
import com.test.db.entity.OrdsTest;
import com.test.db.entity.OrdsTestDTO;
import com.test.db.service.OrdsTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
* 创建或者更新支付订单信息表
* @author lc_xin.
* @date 2017年10月20日
* */
@Component
public class JgOrdsTestListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(JgOrdsTestListener.class);
    @Autowired
    private OrdsTestService ordsTestService;

    @Override
    public void onMessage(Message m) {
        String payOrderInfo =null;
        try {
            logger.info("测试mq信息----------");
            MapMessage message = (MapMessage) m;
            payOrderInfo = message.getString("payOrderInfo");
            if(!StringUtil.isEmpty(payOrderInfo)){
                try {
                    OrdsTest ordsTest = JSONObject.parseObject(payOrderInfo, OrdsTest.class);
                    if(ordsTest!=null){
                        OrdsTestDTO dto = new OrdsTestDTO();
                        dto.setTname(ordsTest.getTname());
                        ordsTest = ordsTestService.doQuery(dto);
                        if(ordsTest!=null){
                            ordsTest.setId(ordsTest.getId());
                            ordsTestService.doUpdate(ordsTest);
                        }else {
                            ordsTestService.doCreate(ordsTest);
                        }
                    }else {
                        logger.info("测试信息为空！");
                    }
                } catch (Exception e) {
                    logger.info("格式化支付订单信息字符串异常！,异常原因：{},处理失败异常的信息payOrderInfo为{}",e.getMessage(),payOrderInfo);
                }
            }else {
                logger.info("支付订单信息为空！");
            }
            logger.info("创建或者更新支付订单信息表mq处理完成----------");
        } catch (Exception e) {
            logger.info("创建或者更新支付订单信息表mq信息处理异常=============>>error{},处理失败异常的信息payOrderInfo为{}", e.getMessage(),payOrderInfo);
        }
    }
}
