package com.worker.infra.mapper.worker;

import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 工人信息Mapper接口
 *
 * @date: 2023/11/9 21:16
 */
@Mapper
public interface WorkerInfoMapper extends BaseMapper<WorkerInfoDO> {

    /**
     * 根据角色Id更新角色名称
     *
     * @param roleId 角色ID
     * @param roleName 角色名称
     * @return 是否成功
     */
    int updateWorkerInfoByRoleId(@Param("roleId") Long roleId, @Param("roleName") String roleName);
}