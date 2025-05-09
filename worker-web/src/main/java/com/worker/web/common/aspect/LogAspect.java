package com.worker.web.common.aspect;

import com.worker.common.base.object.JsonMapper;
import com.worker.common.utils.IpAddressUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 日志切面
 *
 *  @author
 * @date: 2023-11-4 02:29
 */
@Slf4j
@Component
@Aspect
@Order(-98)
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切点
     */
    @Pointcut("execution(public * com.worker.web.rest..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取http请求对象
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 请求接口方法名称
        String apiMethod = getApiOperation(joinPoint);
        // 方法路径
        String methodName = joinPoint.getSignature().getDeclaringTypeName()
                + "." + joinPoint.getSignature().getName();
        // 获取IP地址
        String iP = IpAddressUtils.getIp(request);
        // 请求入参
        String requestParam = JsonMapper.writeValueAsString(Arrays.stream(joinPoint.getArgs())
                .filter(param -> !(param instanceof HttpServletRequest)
                        && !(param instanceof HttpServletResponse)
                        && !(param instanceof MultipartFile)
                        && !(param instanceof MultipartFile[])
                ).collect(Collectors.toList()));

        // 开始打印关键信息
        log.info(
                "[Api Method]|{}|methodName->{}|IP->{}|requestParam->{}|",
                apiMethod, methodName, iP, requestParam
        );

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info(
                "[Api Method]|{}|接口耗时->{}ms|result->{}|",
                apiMethod,
                System.currentTimeMillis() - begin,
                JsonMapper.writeValueAsString(result)
        );
        return result;
    }

    /**
     * 获取Controller的方法名
     */
    private String getApiOperation(ProceedingJoinPoint joinPoint) {
        Method[] methods = joinPoint.getSignature().getDeclaringType().getMethods();
        for (Method method : methods) {
            if (StringUtils.equalsIgnoreCase(method.getName(), joinPoint.getSignature().getName())) {
                ApiOperation annotation = method.getAnnotation(ApiOperation.class);
                if (ObjectUtils.isNotEmpty(annotation)) {
                    return annotation.value();
                }
            }
        }
        return "该Controller的方法使用未使用注解@ApiOperation,请使用该注解说明方法作用";
    }
}
