package com.worker.web.common.exception;

import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.Result;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.LoggerUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

import static com.worker.common.constant.ResponseStatus.ILLEGAL_ARGUMENT;

/**
 * 全局异常处理
 *
 *  @author
 * @date: 2023/11/2 22:57
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * 参数校验失败异常
     *
     * @param exception 校验失败异常
     * @return 响应数据
     */
    @ExceptionHandler(ValidationException.class)
    public Result<Object> handle(ValidationException exception) {
        List<String> errors = null;
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            errors = violations.stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        }
        if (ObjectUtil.isNotEmpty(exception.getCause())) {
            LoggerUtil.userErrorLog(LOGGER, "参数校验失败异常 -> ", exception);
        }
        return new Result<>(ILLEGAL_ARGUMENT.getCode(), String.join(",", errors));
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Result<Object> handlerBizException(BizException e) {
        String message = e.getMessage();
        if (e.getArgs() != null) {
            message = String.format(message, (Object[]) e.getArgs());
        }
        if (e.getErrCode() == 0) {
            return new Result<>(ResponseStatus.SECOND_INVOKER_FAIL.getCode(), message);
        }
        return new Result<>(e.getErrCode(), message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<Object> handlerBindException(BindException ex) {
        LoggerUtil.userErrorLog(LOGGER, "绑定异常:", ex.getMessage(), ex);
        Result<Object> result = Result.error(ResponseStatus.INTERNAL_SERVER_ERROR);
        result.setMessage(StringUtils.join(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(), ",")
        );
        return result;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<Object> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        LoggerUtil.userErrorLog(LOGGER, "Http 消息不可读异常:", ex.getMessage(), ex);
        return Result.error(ILLEGAL_ARGUMENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LoggerUtil.userErrorLog(LOGGER, "方法参数无效异常:", ex.getMessage(), ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, Object> data = new HashMap<>(8);
        if (!fieldErrors.isEmpty() && fieldErrors.get(0) != null) {
            data.put("field", Optional.of(fieldErrors.get(0).getField()).orElse(""));
            data.put("rejectedValue", Optional.ofNullable(fieldErrors.get(0).getRejectedValue()).orElse(""));

            //设置返回错误状态码
            Result<Object> error = Result.error(data);
            error.setCode(ResponseStatus.PARAMETER_ERROR.getCode());
            error.setMessage(Optional.ofNullable(fieldErrors.get(0).getDefaultMessage()).orElse(""));
            return error;
        }
        return new Result<>(
                ResponseStatus.PARAMETER_ERROR.getCode(),
                ResponseStatus.PARAMETER_ERROR.getMsg()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result<Object> handlerIllegalArgumentException(IllegalArgumentException ex) {
        LoggerUtil.userErrorLog(LOGGER, "非法参数异常:", ex.getMessage(), ex);
        return new Result<>(
                ILLEGAL_ARGUMENT.getCode(), ex.getMessage()
        );
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseBody
    public Result<Object> handlerUnexpectedTypeException(UnexpectedTypeException ex) {
        LoggerUtil.userErrorLog(LOGGER, "意外类型异常:", ex.getMessage(), ex);
        return Result.error(ILLEGAL_ARGUMENT);
    }

    /**
     * 捕捉范围->全部异常 兜底异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<Object> handlerAllExceptionHandler(Exception ex) {
        LoggerUtil.userErrorLog(LOGGER, "全局异常:", ex.getMessage(), ex);
        return Result.error(ResponseStatus.INTERNAL_SERVER_ERROR);
    }
}
