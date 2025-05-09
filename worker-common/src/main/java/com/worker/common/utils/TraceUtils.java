package com.worker.common.utils;

import java.util.UUID;

/**
 * 链路工具类
 *
 *  @author
 * @date: 2023/11/2 21:32
 */
public class TraceUtils {
    public static final String TRACE_ID = "traceId";

    /**
     * 生成traceId
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
