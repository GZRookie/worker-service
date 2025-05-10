package com.worker.biz.service.user.impl;

import com.worker.biz.constants.CommonResponseConstants;
import com.worker.biz.constants.role.RoleRequestEnum;
import com.worker.biz.constants.user.AdminUserResponseStatus;
import com.worker.biz.manager.user.AdminUserManager;
import com.worker.client.api.user.AdminUserService;
import com.worker.client.response.role.RoleBaseDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.client.response.user.TokenDTO;
import com.worker.client.request.user.*;
import com.worker.common.base.exception.BizException;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.common.constant.ResponseStatus;
import cn.dev33.satoken.stp.StpUtil;
import com.worker.infra.enums.WorkerRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.worker.biz.constants.user.AdminUserResponseStatus.*;

/**
 * 后台用户实现类
 *
 *  @author
 * @date: 2023/11/3 11:33
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServiceImpl.class);

    @Resource
    private AdminUserManager adminUserManager;

    @Override
    public Result<TokenDTO> login(PwdLoginRequest pwdLoginRequest) {
        Long adminUserId = adminUserManager.login(pwdLoginRequest);
        if(Objects.isNull(adminUserId)) {
            throw new BizException(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), AdminUserResponseStatus.LOGIN_ERROR.getMsg());
        }
        return Result.success(TokenDTO.builder()
                .adminUserId(adminUserId)
                .tokenName(StpUtil.getTokenName())
                .tokenValue(StpUtil.getTokenValue())
                .build());
    }

    @Override
    public Result<Boolean> update(PwdLoginRequest pwdLoginRequest) {
        Boolean update = adminUserManager.update(pwdLoginRequest);
        return Result.success(update);
    }

    @Override
    public Result<Boolean> logout(PwdLogoutRequest pwdLogoutRequest) {
        Boolean logout = adminUserManager.logout(pwdLogoutRequest);
        return Result.success(logout);
    }

    @Override
    public Result<AdminUserPermissionInfoDTO> queryAdminUserPermissionInfo() {
        return Result.success(adminUserManager.queryAdminUserPermissionInfo());
    }

    @Override
    public Result<BasePage<AdminUserPageDTO>> pageAdminUser(AdminUserPageRequest request) {
        BasePage<AdminUserPageDTO> page = adminUserManager.pageAdminUser(request);
        if (Objects.isNull(page.getData())) {
            return Result.success(BasePage.empty());
        }
        return Result.success(page);
    }

    @Override
    public Result<Boolean> judgeWorkerRole(Long roleId) {
        return Result.success(WorkerRoleEnum.judgeWorkerRole(roleId));
    }

    @Override
    public Result<Boolean> addAdminUser(AdminUserRequest request) {
        boolean isAdd = adminUserManager.addAdminUser(request);
        return isAdd ? Result.success() : Result.error(CommonResponseConstants.CREATED_FAIL);
    }

    @Override
    public Result<List<RoleBaseDTO>> showAdminUserDetail(Long adminUserId) {
        return Result.success(adminUserManager.showAdminUserDetail(adminUserId));
    }

    @Override
    public Result<Boolean> editAdminUser(AdminUserRequest request) {
        if(RoleRequestEnum.EDIT.getBizType().equals(request.getRequestType())
                && Objects.isNull(request.getId())) {
            throw new BizException(ADMIN_USER_ID_NOT_NULL);
        }
        boolean isEdit = adminUserManager.editAdminUser(request);
        return isEdit ? Result.success() : Result.error(CommonResponseConstants.UPDATED_FAIL);
    }

    @Override
    public Result<Boolean> deleteAdminUser(AdminUserDeleteRequest request) {
        if(Objects.isNull(request.getDelete())) {
            throw new BizException(DELETE_PARAM_IS_NULL);
        }
        boolean isSuccess = adminUserManager.deleteAdminUser(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.DELETED_FAIL);
    }

    @Override
    public Result<Boolean> enableAdminUser(AdminUserEnableRequest request) {
        if(Objects.isNull(request.getEnable())) {
            throw new BizException(ENABLE_PARAM_IS_NULL);
        }
        boolean isSuccess = adminUserManager.enableAdminUser(request);
        return isSuccess ? Result.success() : Result.error(CommonResponseConstants.ENABLE_FAIL);
    }
}
