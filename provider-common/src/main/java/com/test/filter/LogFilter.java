package com.test.filter;

import com.alibaba.dubbo.rpc.*;
import com.myproject.util.StringUtil;
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
        }
        RpcContext.getContext().getAttachments().put("TRACE_ID",traceId);
        MDC.put("TRACE_ID",traceId);
        return invoker.invoke(invocation);
    }
}
