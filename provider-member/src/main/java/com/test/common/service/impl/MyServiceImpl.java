package com.test.common.service.impl;

import com.myproject.json.JsonUtils;
import com.test.common.queue.QueueSender;
import com.test.db.entity.OrdsTest;
import com.test.db.entity.OrdsTestDTO;
import com.test.db.service.OrdsTestService;
import com.test.member.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/16.
 */
@Service("myService")
public class MyServiceImpl implements MyService {

    @Resource
    QueueSender queueSender;
    @Value("#{configProperties['jgOrdsTestDestination']}")
    protected String jgOrdsTestDestination;
    @Autowired
    private OrdsTestService ordsTestService;

    @Override
    public OrdsTest test(String tname) {

        OrdsTestDTO dto = new OrdsTestDTO();
        dto.setTname(tname);
        OrdsTest ordsTest = ordsTestService.doQuery(dto);
        Map<String, String> map = new HashMap<>();
        map.put("ordsTest", JsonUtils.toJson(ordsTest));
        queueSender.sendMap(jgOrdsTestDestination, map);
        return ordsTest;
    }
}
