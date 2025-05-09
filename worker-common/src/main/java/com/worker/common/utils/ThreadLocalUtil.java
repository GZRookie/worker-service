package com.worker.common.utils;

import com.worker.common.constant.ThreadLocalConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 用户请求ThreadLocal
 *
 *  @author
 * @date: 2023/11/2 23:18
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new HashMap<>(64));

    private static Map<String, Object> getRequestThreadLocal() {
        return THREAD_LOCAL.get();
    }

    private static <T> T get(String key) {
        return get(key, null);
    }


    private static <T> T get(String key, T defaultValue) {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (T) Optional.ofNullable(map.get(key)).orElse(defaultValue);
    }

    private static void set(String key, Object value) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.put(key, value);
    }

    private static void set(Map<String, Object> keyValueMap) {
        Map<String, Object> map = THREAD_LOCAL.get();
        map.putAll(keyValueMap);
    }

    /**
     * 清空所以key
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 移出某key
     *
     * @param key key
     * @param <T> 泛型
     * @return value
     */
    public static <T> T remove(String key) {
        Map<String, Object> map = THREAD_LOCAL.get();
        return (T) map.remove(key);
    }

    public static Long getAdminUserId() {
        return ThreadLocalUtil.get(ThreadLocalConstants.ADMIN_USER_ID);
    }

    public static void setAdminUserId(Long userId) {
        ThreadLocalUtil.set(ThreadLocalConstants.ADMIN_USER_ID, userId);
    }

    public static void setAddressIp(String ip) {
        ThreadLocalUtil.set(ThreadLocalConstants.ADDRESS_IP, ip);
    }

    public static void setFrontUserType(String frontUserType) {
        ThreadLocalUtil.set(ThreadLocalConstants.FRONT_USER_TYPE, frontUserType);
    }

    public static String getFrontUserType() {
        return ThreadLocalUtil.get(ThreadLocalConstants.FRONT_USER_TYPE);
    }

    public static void setEnterpriseUserId(Long userId) {
        ThreadLocalUtil.set(ThreadLocalConstants.ENTERPRISE_USER_ID, userId);
    }

    public static Long getEnterpriseUserId() {
        return ThreadLocalUtil.get(ThreadLocalConstants.ENTERPRISE_USER_ID);
    }

    public static void setTenantId(Long userId) {
        ThreadLocalUtil.set(ThreadLocalConstants.TENANT_ID, userId);
    }

    public static Long getTenantId() {
        return ThreadLocalUtil.get(ThreadLocalConstants.TENANT_ID);
    }

    public static void setMasterId(Long userId) {
        ThreadLocalUtil.set(ThreadLocalConstants.MASTER_ID, userId);
    }

    public static Long getMasterId() {
        return ThreadLocalUtil.get(ThreadLocalConstants.MASTER_ID);
    }

    public static void setPersonalId(Long userId) {
        ThreadLocalUtil.set(ThreadLocalConstants.PERSONAL_ID, userId);
    }

    public static Long getPersonalId() {
        return ThreadLocalUtil.get(ThreadLocalConstants.PERSONAL_ID);
    }
}
