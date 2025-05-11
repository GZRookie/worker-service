package com.worker.infra.dao.user;

import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 后台用户访问层类
 *
 * @author: zhen.gong
 * @date: 2023/11/3 14:45
 */
public interface AdminUserDao {

    /**
     * 根据手机号查询后台用户信息
     *
     * @param phoneNum 手机号
     * @return 后台用户信息
     */
    AdminUserInfoDO queryAdminUserInfoByPhoneNum(String phoneNum);

    /**
     * 根据手机号查询后台用户信息
     *
     * @param phoneNum 手机号
     * @return 后台用户信息
     */
    AdminUserInfoDO queryEditAdminUserInfoByPhoneNum(String phoneNum, Long id);

    /**
     * 修改后台用户信息
     *
     * @param adminUserInfoDO 后台用户实体类
     * @return 修改是否成功
     */
    boolean updateAdminUserInfo(AdminUserInfoDO adminUserInfoDO);

    /**
     * 列表分页查询
     *
     * @param adminUserPageQueryDO 分页参数
     * @return 分页数据
     */
    IPage<AdminUserPageDO> pageAdminUser(AdminUserPageQueryDO adminUserPageQueryDO);

    /**
     * 新增账号
     *
     * @param adminUserDO 实体类
     * @return 新增是否成功
     */
    boolean addAdminUser(AdminUserInfoDO adminUserDO);

    /**
     * 编辑账号
     *
     * @param adminUserDO 实体类
     * @return 编辑是否成功
     */
    boolean editAdminUser(AdminUserInfoDO adminUserDO);

    /**
     * 根据id批量查询未删除已启用的用户信息
     *
     * @param adminUserIds 用户ids
     * @return 未删除已启用的用户信息
     */
    List<AdminUserInfoDO> queryAdminUserInfoByIds(List<Long> adminUserIds);

    /**
     * 查询用户信息
     *
     * @return 用户信息
     */
    List<AdminUserInfoDO> queryAdminUserInfoList();

    /**
     * 查询用户
     *
     * @param sysUserId 用户id
     * @return 用户信息
     */
    AdminUserInfoDO queryAdminUserInfoById(Long sysUserId);
}
