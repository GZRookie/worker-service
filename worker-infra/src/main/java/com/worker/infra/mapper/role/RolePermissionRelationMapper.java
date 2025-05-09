package com.worker.infra.mapper.role;

import com.worker.infra.dataobject.role.RolePermissionRelationDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper
 *
 *  @author
 * @date: 2023/11/8 16:04
 */
@Mapper
public interface RolePermissionRelationMapper extends BaseMapper<RolePermissionRelationDO> {
}
