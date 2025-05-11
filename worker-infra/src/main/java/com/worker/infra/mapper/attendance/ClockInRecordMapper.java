package com.worker.infra.mapper.attendance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.worker.infra.dataobject.attendance.ClockInRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 打卡Mapper接口
 *
 * @date: 2023/11/9 21:16
 */
@Mapper
public interface ClockInRecordMapper extends BaseMapper<ClockInRecordDO> {
}