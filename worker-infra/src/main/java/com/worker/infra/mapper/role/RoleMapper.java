package com.worker.infra.mapper.role;

import com.worker.infra.dataobject.role.RoleDO;
import com.worker.infra.dataobject.role.RolePageDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色Mapper接口
 *
 *  @author
 * @date: 2023/11/8 15:16
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    /**
     * 角色分页查询
     *
     * @param page 分页信息
     * @param roleDO 角色实体类
     * @return 角色分页数据
     */
    IPage<RolePageDO> selectRolePage(IPage<RoleDO> page, @Param("condition")RoleDO roleDO);
}
