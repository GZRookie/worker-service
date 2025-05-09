package com.worker.common.base.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 数据库异常错误类型
 *
 *  @author
 * @date: 2023/11/3 15:16
 */
@AllArgsConstructor
@NoArgsConstructor
public class DBExceptionType implements ErrorType {
    private int code;
    private String message;

    public static final DBExceptionType QUERY_EXCEPTION = new DBExceptionType(100, "数据库查询异常");
    public static final DBExceptionType INSERT_EXCEPTION = new DBExceptionType(101, "数据库插入异常");
    public static final DBExceptionType UPDATE_EXCEPTION = new DBExceptionType(102, "数据库更新异常");
    public static final DBExceptionType DELETE_EXCEPTION = new DBExceptionType(103, "数据库删除异常");
    public static final DBExceptionType DUPLICATE_KEY_EXCEPTION = new DBExceptionType(104, "数据库重名异常");
    public static final DBExceptionType QUERY_PARAM_INVALID = new DBExceptionType(105, "数据库查询参数无效");
    public static final DBExceptionType INSERT_PARAM_INVALID = new DBExceptionType(106, "数据库插入参数无效");
    public static final DBExceptionType UPDATE_PARAM_INVALID = new DBExceptionType(107, "数据库更新参数无效");
    public static final DBExceptionType DELETE_PARAM_INVALID = new DBExceptionType(108, "数据库删除参数无效");

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.message;
    }
}
