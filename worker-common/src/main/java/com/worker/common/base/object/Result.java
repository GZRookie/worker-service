package com.worker.common.base.object;

import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.TraceUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.MDC;

import java.util.Formatter;

/**
 * 响应类
 *
 *  @author
 * @date: 2023/11/2 22:59
 */
@Getter
@Setter
public class Result<T> {
    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "状态描述")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "返回时间")
    private String date;

    @ApiModelProperty(value = "请求Id")
    private String requestId;

    public Result() {
        this.date = DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        this.requestId = MDC.get(TraceUtils.TRACE_ID);
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.date = DateTime.now().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        this.requestId = MDC.get(TraceUtils.TRACE_ID);
    }

    /**
     * 请求是否是成功的
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return this.code == ResponseStatus.SUCCESS.getCode();
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage(ResponseStatus.SUCCESS.getMsg());
        result.setCode(ResponseStatus.SUCCESS.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }

    public static <T> Result<T> success(ResponseStatus responseStatus) {
        Result<T> result = new Result<>();
        result.setData(null);
        result.setMessage(responseStatus.getMsg());
        result.setCode(ResponseStatus.SUCCESS.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setData(null);
        result.setMessage(ResponseStatus.SUCCESS.getMsg());
        result.setCode(ResponseStatus.SUCCESS.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }

    public static <T> Result<T> error(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMsg());
        result.setCode(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }

    public static <T> Result<T> error(ResponseStatus responseStatus) {
        Result<T> result = new Result<>();
        result.setData(null);
        result.setMessage(responseStatus.getMsg());
        result.setCode(responseStatus.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }

    public static <T> Result<T> error(ResponseStatus responseStatus, Object... args) {
        Result<T> result = new Result<>();
        result.setData(null);
        if (StringUtils.isNotEmpty(responseStatus.getMsg())) {
            String msg = new Formatter().format(responseStatus.getMsg(), args).toString();
            result.setMessage(msg);
        } else {
            result.setMessage(responseStatus.getMsg());
        }
        result.setCode(responseStatus.getCode());
        result.setRequestId(MDC.get(TraceUtils.TRACE_ID));
        return result;
    }
}
