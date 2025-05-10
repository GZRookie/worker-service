package com.worker.infra.mapper.worker;

import com.worker.infra.dataobject.worker.WorkerInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工人信息Mapper接口
 *
 * @date: 2023/11/9 21:16
 */
@Mapper
public interface WorkerInfoMapper extends BaseMapper<WorkerInfoDO> {
}