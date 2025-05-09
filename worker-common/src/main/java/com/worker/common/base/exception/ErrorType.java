package com.worker.common.base.exception;

/**
 * 定义错误类型
 *
 *  @author
 * @date: 2023/11/2 22:04
 */
public interface ErrorType {
    /**
     * 返回code
     *
     * @return
     */
    int getCode();

    /**
     * 返回msg
     *
     * @return
     */
    String getMsg();
}
