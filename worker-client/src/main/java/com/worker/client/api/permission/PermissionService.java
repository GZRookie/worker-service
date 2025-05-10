package com.worker.client.api.permission;

import com.worker.client.response.permisiion.PermissionDTO;
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
     * @return 列表页
     */
    Result<List<PermissionDTO>> listPermission();
}
