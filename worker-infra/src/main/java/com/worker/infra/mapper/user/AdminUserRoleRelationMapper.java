package com.worker.infra.mapper.user;

import com.worker.infra.dataobject.user.AdminUserRoleRelationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户关联关系Mapper接口
 *
 *  @author
 * @date: 2023/11/9 21:16
 */
@Mapper
public interface AdminUserRoleRelationMapper extends BaseMapper<AdminUserRoleRelationDO> {
}
