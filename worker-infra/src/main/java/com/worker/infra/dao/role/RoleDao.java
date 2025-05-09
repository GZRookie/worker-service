package com.worker.infra.dao.role;

import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePageDO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 角色访问层类
 *
 * @author: zhen.gong
 * @date: 2023/11/8 14:28
 */
public interface RoleDao {

    /**
     * 查询角色页面
     *
     * @param roleDO 角色类
     * @param offset 起始页
     * @param limit 条数
     * @return 当前页数据
     */
    IPage<RolePageDO> pageRole(RoleDO roleDO, Integer offset, Integer limit);

    /**
     * 查询未删除角色名称是否存在
     *
     * @param roleName 角色名称
     * @return 是否存在
     */
    boolean queryRoleNameExist(String roleName);

    /**
     * 查询未删除角色名称是否存在
     *
     * @param roleName 角色名称
     * @return 是否存在
     */
    boolean queryEditRoleNameExist(String roleName, Long id);

    /**
     * 新增角色
     *
     * @param roleDO 角色实体
     * @return 是否成功
     */
    boolean addRole(RoleDO roleDO);

    /**
     * 编辑角色
     *
     * @param roleDO 角色实体
     * @return 是否成功
     */
    boolean updateRole(RoleDO roleDO);

    /**
     * 查询角色列表
     *
     * @return 角色列表
     */
    List<RoleDO> queryRoleList();

    /**
     * 查询角色信息
     *
     * @param roleIds 角色ids
     * @return 角色信息
     */
    List<RoleDO> queryRoleByIds(List<Long> roleIds);
}
