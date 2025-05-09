package com.worker.infra.dao.role;

import com.worker.infra.dataobject.role.RolePermissionRelationDO;

import java.util.List;

/**
 * 角色关联权限访问层类
 *
 * @author: zhen.gong
 * @date: 2023/11/8 16:00
 */
public interface RolePermissionRelationDao {

    /**
     * 添加角色权限关联关系
     *
     * @param rolePermissionRelationDO 角色权限关联关系实体类
     * @return 添加是否成功
     */
    boolean addRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO);

    /**
     * 更新角色权限关联关系
     *
     * @param rolePermissionRelationDO 角色权限关联关系实体类
     * @return 更新是否成功
     */
    boolean updateRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO);

    /**
     * 根据角色id查询权限ids
     *
     * @param id 角色id
     * @return 实体类
     */
    List<RolePermissionRelationDO> queryPermissionByRoleId(Long id);

    /**
     * 根据角色id查询权限ids
     *
     * @param roleIds 角色id
     * @return 实体类
     */
    List<RolePermissionRelationDO> queryPermissionByRoleIds(List<Long> roleIds);

    /**
     * 删除角色权限关系
     *
     * @param rolePermissionRelationDO 实体类
     * @return 是否成功
     */
    boolean delRolePermissionRelation(RolePermissionRelationDO rolePermissionRelationDO);
}
