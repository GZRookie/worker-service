package com.worker.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则校验工具类
 *
 *  @author
 * @date: 2023/11/3 16:30
 */
public class RegularUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegularUtils.class);

    /**
     * 手机号校验
     */
    public static final String MOBILE = "^1\\d{10}$";

    /**
     * 密码格式校验 纯数字或数字加字母组合超过6位
     */
    public static final String PASSWORD = "^\\d{6,}|[a-zA-Z0-9]{7,}$";

    /**
     * 正则表达式校验数据
     *
     * @param param      需要校验的值
     * @param expression 正则表达式
     * @return 校验是否通过
     */
    public static boolean checkParamsByRegular(String param, String expression) {
        try {
            if (StringUtils.isEmpty(param) || StringUtils.isEmpty(expression)) {
                return false;
            }
            Pattern pattern = Pattern.compile(expression);
            Matcher match = pattern.matcher(param);
            return match.matches();
        } catch (Exception e) {
            LoggerUtil.userErrorLog(LOGGER, "checkParamsByRegular exception", param, e);
            return false;
        }
    }
}
