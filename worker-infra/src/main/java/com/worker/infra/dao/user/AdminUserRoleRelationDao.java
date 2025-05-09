package com.worker.infra.dao.user;

import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;

import java.util.List;

/**
 * 后台用户角色关系关联访问层类
 *
 * @author: zhen.gong
 * @date: 2023/11/9 21:13
 */
public interface AdminUserRoleRelationDao {
    /**
     * 新增用户角色关联关系
     *
     * @param adminUserRoleRelationDO 实体类
     * @return 新增是否成功
     */
    boolean addAdminUserRoleRelation(AdminUserRoleRelationDO adminUserRoleRelationDO);

    /**
     * 删除用户角色关联关系
     *
     * @param adminUserRoleRelationDO 实体类
     * @return 是否成功
     */
    boolean delAdminUserRoleRelation(AdminUserRoleRelationDO adminUserRoleRelationDO);

    /**
     * 根据userId查询用户角色关联关系
     *
     * @param userId userId
     * @return 用户角色关联关系
     */
    List<AdminUserRoleRelationDO> queryRoleByUserId(Long userId);

    /**
     * 根据角色ids查询用户角色关联关系
     *
     * @param roleIds 角色ids
     * @return 用户角色关联关系
     */
    List<AdminUserRoleRelationDO> queryUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 根据roleId查询用户角色关联关系
     *
     * @param roleId roleId
     * @return 用户角色关联关系
     */
    List<AdminUserRoleRelationDO> queryAdminUserRoleRelationsByRoleId(Long roleId);
}
