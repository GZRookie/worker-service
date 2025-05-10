package com.worker.web.common.aspect;

import com.worker.client.api.user.AdminUserService;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.Result;
import com.worker.common.constant.ResponseStatus;
import com.worker.common.utils.ThreadLocalUtil;
import com.worker.web.common.anno.CheckOperateAuth;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作权限切面
 *
 *  @author
 * @date: 2024-01-10 12:32
 */
@Aspect
@Component
public class OperateAuthAspect {

    @Resource
    private AdminUserService adminUserService;

    /**
     * 执行请求之前
     */
    @Before("execution(* com.worker.web.rest..*.*(..))")
    public boolean before(JoinPoint joinPoint) {
        if (!hasAnnotation(joinPoint)) {
            throw new BizException(ResponseStatus.FORBIDDEN);
        }
        return true;
    }

    /**
     * 判断是否有该注解,并有权限
     *
     * @param joinPoint 切点
     * @return 是否有权限 True-有 False-无
     */
    public boolean hasAnnotation(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取切点上的方法
        Method method = methodSignature.getMethod();
        CheckOperateAuth annotation = method.getAnnotation(CheckOperateAuth.class);
        // 方法上没有获取类上的
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(CheckOperateAuth.class);
        }
        // 如果都没有就不进行鉴权
        if (annotation == null) {
            return true;
        }

        // 检查操作权限
        return checkOperateAuth(annotation.code(), annotation.isPass());
    }

    /**
     * 检查当前用户在当前团队下是否有该操作权限code的权限
     *
     * @param checkCode 待验证权限code
     * @param isPass
     * @return 是否有权限
     */
    private boolean checkOperateAuth(String checkCode, boolean isPass) {
        // 是客户端则直接放行，是后台登录则校验权限码
        if (StringUtils.hasLength(ThreadLocalUtil.getFrontUserType()) ||
                ThreadLocalUtil.getFrontUserType() == null) {
            return true;
        }

        // 获取当前用户ID，当前角色ID
        Long adminUserId = ThreadLocalUtil.getAdminUserId();
        if (adminUserId == null && isPass) {
            return true;
        }

        if (adminUserId == null) {
            return false;
        }

        // code是否为空
        if (!StringUtils.hasLength(checkCode)) {
            return false;
        }

        // 查询当前用户在当前团队下的所有权限Code
        List<String> permissionCodes = new ArrayList<>();
        Result<AdminUserPermissionInfoDTO> userPermissionInfo = adminUserService.queryAdminUserPermissionInfo();
        List<PermissionDTO> permissionList = userPermissionInfo.getData().getPermissionList();
        if(CollectionUtils.isEmpty(permissionList)) {
            return false;
        }
        // 树状转为list
        convertTreeToList(permissionList, permissionCodes);

        // 响应校验
        if (CollectionUtils.isEmpty(permissionCodes)) {
            return false;
        }

        // 权限校验
        return permissionCodes.contains(checkCode);
    }

    /**
     * 树状结构转为列表
     *
     * @param permissionList 树状权限
     * @param permissionCodes 权限码
     */
    private void convertTreeToList(List<PermissionDTO> permissionList, List<String> permissionCodes) {
        for (PermissionDTO permissionDTO : permissionList) {
            permissionCodes.add(permissionDTO.getCode());
        }
    }

}
