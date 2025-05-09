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
     * 新增权限
     *
     * @param permission 权限实体
     * @return 是否成功
     */
    boolean addPermission(PermissionDO permission);

    /**
     * 编辑权限
     *
     * @param permission 权限实体
     * @return 是否成功
     */
    boolean updatePermission(PermissionDO permission);

    /**
     * 查询权限名称是否存在
     *
     * @param name
     * @return
     */
    boolean queryPermissionNameExist(String name);

    /**
     * 查询编辑时权限名称是否存在
     *
     * @param name
     * @param id
     * @return
     */
    boolean queryEditPermissionNameExist(String name, Long id);

    /**
     * 根据权限ids查询权限信息
     *
     * @param permissionIds 权限ids
     * @return
     */
    List<PermissionDO> queryPermissionByIds(List<Long> permissionIds);

    /**
     * 根据id查询权限
     *
     * @param id id
     * @return 权限
     */
    PermissionDO queryPermissionById(Long id);
}
