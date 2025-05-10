package com.worker.infra.dao.permission;

import com.worker.infra.dataobject.permission.PermissionDO;

import java.util.List;

/**
 * 权限访问层类
 *
 * @author: zhen.gong
 * @date: 2023/11/6 15:29
 */
public interface PermissionDao {

    /**
     * 查询权限列表
     *
     * @param permissionDO 实体类
     * @return 数据
     */
    List<PermissionDO> queryPermissionList(PermissionDO permissionDO);

    /**
     * 根据权限ids查询权限信息
     *
     * @param permissionIds 权限ids
     * @return
     */
    List<PermissionDO> queryPermissionByIds(List<Long> permissionIds);
}
