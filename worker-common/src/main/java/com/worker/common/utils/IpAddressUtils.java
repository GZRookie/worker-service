package com.worker.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 获取IP地址工具类
 *
 *  @author
 * @date: 2023/11/3 17:14
 */
public class IpAddressUtils {
    /**
     * 获取目标主机的ip
     *
     * @param request 请求
     * @return 响应
     */
    public static String getIp(HttpServletRequest request) {
        List<String> ipHeadList = Stream.of(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "X-Real-IP"
        ).collect(Collectors.toList());
        for (String ipHead : ipHeadList) {
            if (checkIp(request.getHeader(ipHead))) {
                return request.getHeader(ipHead).split(",")[0];
            }
        }
        return "0:0:0:0:0:0:0:1".equals(
                request.getRemoteAddr()
        ) ? "127.0.0.1" : request.getRemoteAddr();
    }

    /**
     * 检查ip存在
     */
    public static boolean checkIp(String ip) {
        return !(null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip));
    }
}
