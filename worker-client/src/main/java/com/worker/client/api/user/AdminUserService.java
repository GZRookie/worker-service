package com.worker.client.api.user;

import com.worker.client.response.role.RoleBaseDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.client.response.user.TokenDTO;
import com.worker.client.request.user.*;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;

import java.util.List;

/**
 * 后台用户服务类
 *
 *  @author
 * @date: 2023/11/3 11:21
 */
public interface AdminUserService {

    /**
     * 账号密码登录
     *
     * @param pwdLoginRequest 账号密码登陆请求
     * @return token
     */
    Result<TokenDTO> login(PwdLoginRequest pwdLoginRequest);

    /**
     * 密码修改
     *
     * @param pwdLoginRequest 账号密码登陆请求
     * @return token
     */
    Result<Boolean> update(PwdLoginRequest pwdLoginRequest);

    /**
     * 账号密码登出
     *
     * @param pwdLogoutRequest 账号密码登出请求
     * @return token
     */
    Result<Boolean> logout(PwdLogoutRequest pwdLogoutRequest);

    /**
     * 查询用户权限
     *
     * @return
     */
    Result<AdminUserPermissionInfoDTO> queryAdminUserPermissionInfo();

    /**
     * 账号页面查询
     *
     * @param request 分页请求
     * @return 分页数据
     */
    Result<BasePage<AdminUserPageDTO>> pageAdminUser(AdminUserPageRequest request);

    /**
     * 新增账号
     *
     * @param request 新增请求
     * @return 是否成功
     */
    Result<Boolean> addAdminUser(AdminUserRequest request);

    /**
     * 编辑回显
     *
     * @param adminUserId 账号id
     * @return 角色信息
     */
    Result<List<RoleBaseDTO>> showAdminUserDetail(Long adminUserId);

    /**
     * 编辑账号
     *
     * @param request 编辑请求
     * @return 是否成功
     */
    Result<Boolean> editAdminUser(AdminUserRequest request);

    /**
     * 删除账号
     *
     * @param request 删除请求
     * @return 是否成功
     */
    Result<Boolean> deleteAdminUser(AdminUserDeleteRequest request);

    /**
     * 启用账号
     *
     * @param request 启用请求
     * @return 是否成功
     */
    Result<Boolean> enableAdminUser(AdminUserEnableRequest request);
}
