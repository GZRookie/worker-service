package com.worker.common.utils;

import com.worker.common.base.object.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志工具类
 *
 *  @author
 * @date: 2023/11/2 23:12
 */
@Slf4j
@Component
public class LoggerUtil {
    /**
     * error or exception logger
     */
    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("error");

    /**
     * info log print
     *
     * @param logger    logger
     * @param msg       msg，自定义信息
     * @param object    参数对象，如不需要则 null，toJson print
     */
    public static void info(Logger logger, String msg, Object object) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format("%s|%s",  msg, JsonMapper.writeValueAsString(object)));
        }
    }

    /**
     * warn log print
     *
     * @param logger    logger
     * @param msg       msg，自定义信息
     * @param object    参数对象，如不需要则 null，toJson print
     */
    public static void warn(Logger logger, String msg, Object object) {
        if (logger.isWarnEnabled()) {
            logger.warn(String.format("%s|%s", msg, JsonMapper.writeValueAsString(object)));
        }
    }

    /**
     * debug log print
     *
     * @param logger    logger
     * @param msg       msg，自定义信息
     * @param object    参数对象，如不需要则 null，toJson print
     */
    public static void debug(Logger logger, String msg, Object object) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("%s|%s", msg, JsonMapper.writeValueAsString(object)));
        }
    }

    /**
     * error log print(单独文件打印错误、异常信息)
     *
     * @param logger    logger
     * @param msg       msg，自定义信息
     * @param object    参数对象，如不需要则 null，toJson print
     * @param t         异常
     */
    public static void error(Logger logger, String msg, Object object, Throwable t) {
        if (logger.isErrorEnabled()) {
            // 暂不考虑双重打印，如果单独输出日志文件，需要单独打印错误信息到单独的日志文件再做处理
            ERROR_LOGGER.error(String.format("%s|%s", msg, JsonMapper.writeValueAsString(object)), t);
        }
    }


    /**
     * 相关日志打印
     *
     * @param logger
     * @param msg
     * @param object
     */
    public static void userErrorLog(Logger logger, String msg, Object object) {
        error(logger, msg, object, null);
    }

    public static void userErrorLog(Logger logger, String msg, Object object, Throwable t) {
        error(logger, msg, object, t);
    }

    /**
     * 相关日志打印
     *
     * @param logger 日志
     * @param msg    消息
     * @param object 打印对象
     */
    public static void userInfoLog(Logger logger, String msg, Object object) {
        info(logger, msg, object);
    }
}
