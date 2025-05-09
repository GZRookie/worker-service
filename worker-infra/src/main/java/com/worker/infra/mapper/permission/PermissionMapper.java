package com.worker.infra.mapper.permission;

import com.worker.infra.dataobject.permission.PermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper接口
 *
 *  @author
 * @date: 2023/11/6 15:47
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionDO> {
}
