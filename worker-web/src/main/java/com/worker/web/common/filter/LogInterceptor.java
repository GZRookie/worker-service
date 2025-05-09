package com.worker.web.common.filter;

import com.worker.common.utils.TraceUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 *
 *  @author
 * @date: 2023/11/2 21:28
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 设置traceId
        String traceId = generateTraceId(request);
        MDC.put(TraceUtils.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 调用结束后删除
        MDC.remove(TraceUtils.TRACE_ID);
    }

    /**
     * 生成TraceId信息
     *
     * @param request 请求
     * @return traceId
     */
    private String generateTraceId(HttpServletRequest request) {
        //如果有上层调用就用上层的ID
        if (StringUtils.isNotEmpty(request.getHeader(TraceUtils.TRACE_ID))) {
            return request.getHeader(TraceUtils.TRACE_ID);
        }

        // 生成一个
        return TraceUtils.generateTraceId();
    }
}
