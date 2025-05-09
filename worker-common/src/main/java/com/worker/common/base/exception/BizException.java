package com.worker.common.base.exception;

import com.worker.common.constant.ResponseStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 业务异常类
 *
 *  @author
 * @date: 2023/11/2 22:00
 */
@Data
@NoArgsConstructor
public class BizException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 866487632716108427L;
    private int errCode;

    private String[] args;

    public BizException(int code, String msg) {
        super(msg);
        this.errCode = code;
    }

    public BizException(int code, String msg, String[] args) {
        super(msg);
        this.errCode = code;
        this.args = args;
    }

    public BizException(ResponseStatus status) {
        super(status.getMsg());
        this.errCode = status.getCode();
    }

    public BizException(ResponseStatus status, String[] args) {
        super(status.getMsg());
        this.errCode = status.getCode();
        this.args = args;
    }


    public BizException(ErrorType type) {
        super(type.getMsg());
        this.errCode = type.getCode();
    }
}
