package com.worker.client.api.role;

import com.worker.client.request.role.RoleDeleteRequest;
import com.worker.client.request.role.RoleEnableRequest;
import com.worker.client.request.role.RolePageRequest;
import com.worker.client.request.role.RoleRequest;
import com.worker.client.response.permisiion.PermissionBaseNodeDTO;
import com.worker.client.response.role.RoleDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;

import java.util.List;

/**
 * 角色服务类
 *
 *  @author
 * @date: 2023/11/7 23:05
 */
public interface RoleService {
    /**
     * 角色页面查询
     *
     * @param request 查询请求
     * @return 列表页
     */
    Result<BasePage<RoleDTO>> pageRole(RolePageRequest request);

    /**
     * 新增角色
     *
     * @return 是否成功
     */
    Result<Boolean> addRole(RoleRequest request);

    /**
     * 编辑回显
     *
     * @param roleId 角色id
     * @return 权限
     */
    Result<List<PermissionBaseNodeDTO>> showRoleDetail(Long roleId);

    /**
     * 编辑角色
     *
     * @return 是否成功
     */
    Result<Boolean> editRole(RoleRequest request);

    /**
     * 删除角色
     *
     * @param request 角色删除请求
     * @return 是否成功
     */
    Result<Boolean> deleteRole(RoleDeleteRequest request);

    /**
     * 启用角色
     *
     * @param request 角色启用请求
     * @return 是否成功
     */
    Result<Boolean> enableRole(RoleEnableRequest request);

    /**
     * 已启用未删除的角色
     *
     * @return 角色列表
     */
    Result<List<RoleDTO>> list();
}
