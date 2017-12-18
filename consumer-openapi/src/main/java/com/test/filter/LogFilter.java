package com.test.filter;

import com.alibaba.dubbo.rpc.*;
import com.test.common.utils.StringUtil;
import org.slf4j.MDC;

/**
 * Created by fancz on 2017/9/11.
 */
public class LogFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachments().get("TRACE_ID");
        if(StringUtil.isEmpty(traceId)){
            traceId = MDC.get("TRACE_ID");
            //此处不做生成跟踪号，意味着不经过apiController的dubbo方面内部请求，则忽略不记录traceId
            //if (StringUtil.isEmpty(traceId)){
            //    traceId = DateUtil.getRandomTraceId();
            //}
        }
        MDC.put("TRACE_ID",traceId);
        RpcContext.getContext().getAttachments().put("TRACE_ID",traceId);
        return invoker.invoke(invocation);
    }
}
