package com.worker.biz.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏工具类
 *
 *  @author
 * @date: 2023-11-4 02:31
 */
public class DesensitizationUtil {
    private static final int EMAIL_DESENSITIZATION_LENGTH = 4;

    /**
     * [中文姓名] 只显示第一个汉字，其他隐藏为星号<例子：李**>
     *
     * @param fullName 姓名
     * @return 中文脱敏
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [中文姓名] 只显示姓氏，其他隐藏为星号<例子：欧阳娜娜  ： 欧阳**>
     *
     * @param familyName 姓氏
     * @param givenName  名字
     * @return 中文脱敏
     */
    public static String chineseName(String familyName, String givenName) {
        if (StringUtils.isBlank(familyName) || StringUtils.isBlank(givenName)) {
            return "";
        }
        if (familyName.length() > 1) {
            String name = StringUtils.left(familyName, familyName.length());
            return StringUtils.rightPad(name, StringUtils.length(familyName + givenName), "*");
        }
        return chineseName(familyName + givenName);
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。<例子：*************5762>
     *
     * @param id 身份证号
     * @return 身份证号
     */
    public static String idCardNum(String id) {
        if (StringUtils.isBlank(id)) {
            return "";
        }
        String num = StringUtils.right(id, 4);
        return StringUtils.leftPad(num, StringUtils.length(id), "*");
    }


    /**
     * [身份证号] 前六位，后四位，其他用星号隐藏每位1个星号<例子:451002********1647>
     *
     * @param carId 身份证号
     * @return 身份证号
     */
    public static String idCard(String carId) {
        if (StringUtils.isBlank(carId)) {
            return "";
        }
        return StringUtils.left(carId, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(carId, 4), StringUtils.length(carId), "*"), "******"));
    }

    /**
     * [固定电话] 后四位，其他隐藏<例子：****1234>
     *
     * @param num 电话
     * @return 固定电话
     */
    public static String fixedPhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param num 手机号
     * @return 脱敏后手机号
     */
    public static String mobilePhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"), "***"));
    }

    /**
     * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param address       地址脱敏
     * @param sensitiveSize 敏感信息长度
     * @return 脱敏后地址
     */
    public static String address(String address, int sensitiveSize) {
        if (StringUtils.isBlank(address)) {
            return "";
        }
        int length = StringUtils.length(address);
        return StringUtils.rightPad(StringUtils.left(address, length - sensitiveSize), length, "*");
    }

    /**
     * [电子邮箱] 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     *
     * @param email 邮箱
     * @return 邮箱脱敏
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }
        int index = StringUtils.indexOf(email, "@");
        if (index <= EMAIL_DESENSITIZATION_LENGTH) {
            return email;
        } else {
            return StringUtils.rightPad(StringUtils.left(email, 4), index, "*").concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }
}
