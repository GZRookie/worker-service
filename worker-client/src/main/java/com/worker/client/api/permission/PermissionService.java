package com.worker.client.api.permission;

import com.worker.client.request.permission.PermissionDeleteRequest;
import com.worker.client.request.permission.PermissionPageRequest;
import com.worker.client.request.permission.PermissionRequest;
import com.worker.client.request.permission.PermissionEnableRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.common.base.object.Result;

import java.util.List;

/**
 * 权限服务类
 *
 *  @author
 * @date: 2023/11/5 14:30
 */
public interface PermissionService {

    /**
     * 权限页面查询
     *
     * @param request 查询请求
     * @return 列表页
     */
    Result<List<PermissionNodeDTO>> listPermission(PermissionPageRequest request);

    /**
     * 新增权限
     *
     * @return 是否成功
     */
    Result<Boolean> addPermission(PermissionRequest request);

    /**
     * 编辑权限
     *
     * @return 是否成功
     */
    Result<Boolean> editPermission(PermissionRequest request);

    /**
     * 删除权限
     *
     * @param request 权限删除请求
     * @return 是否成功
     */
    Result<Boolean> deletePermission(PermissionDeleteRequest request);

    /**
     * 启用权限
     *
     * @param request 权限启用请求
     * @return 是否成功
     */
    Result<Boolean> enablePermission(PermissionEnableRequest request);
}
