package com.worker.infra.mapper.user;

import com.worker.infra.dataobject.user.AdminUserInfoDO;
import com.worker.infra.dataobject.user.AdminUserPageDO;
import com.worker.infra.dataobject.user.AdminUserPageQueryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 后台用户Mapper接口
 *
 *  @author
 * @date: 2023/11/3 15:08
 */
@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUserInfoDO> {

    /**
     * 账号分页
     *
     * @param page 分页条件
     * @param adminUserPageQueryDO 查询条件
     * @return 分页数据
     */
    IPage<AdminUserPageDO> selectAdminUserPage(IPage<AdminUserInfoDO> page, @Param("condition") AdminUserPageQueryDO adminUserPageQueryDO);
}
