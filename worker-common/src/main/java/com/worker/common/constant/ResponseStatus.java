package com.worker.common.constant;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局接口响应码表
 *
 *  @author
 * @date: 2023/11/2 22:02
 */
@Data
@NoArgsConstructor
public class ResponseStatus {
    private int code;

    private String msg;

    public ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseStatus(int code, String msg, Object... args) {
        this.code = code;

    }

    public static final ResponseStatus SUCCESS = new ResponseStatus(2000, "请求成功");
    public static final ResponseStatus ILLEGAL_ARGUMENT = new ResponseStatus(4000, "非法参数");

    public static final ResponseStatus UNAUTHORIZED = new ResponseStatus(4001, "token无效");

    public static final ResponseStatus FORBIDDEN = new ResponseStatus(4003, "没有访问权限");

    public static final ResponseStatus NOT_FOUND = new ResponseStatus(4004, "请求资源不存在");
    public static final ResponseStatus RESOURCE_EXIST = new ResponseStatus(4006, "请求资源已存在");

    public static final ResponseStatus PARAMETER_ERROR = new ResponseStatus(4005, "参数错误");
    public static final ResponseStatus INTERFACE_REPEAT_SUBMISSION = new ResponseStatus(4007, "请求繁忙,请稍后再试");
    public static final ResponseStatus SECOND_INVOKER_FAIL = new ResponseStatus(5999, "服务调用失败");
    public static final ResponseStatus INTERNAL_SERVER_ERROR = new ResponseStatus(5000, "服务开小差啦,请稍后再试");
    public static final ResponseStatus RESPONSE_IS_NULL = new ResponseStatus(5998, "响应为空");
}
