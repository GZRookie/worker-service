package com.worker.infra.dataobject.attendance;

import com.worker.infra.dataobject.BasePageDO;
import com.worker.infra.dataobject.worker.WorkerInfoDO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 工人信息分页查询对象
 *
 * @date: 2023/11/9 16:04
 */
@Getter
@Setter
public class AttendanceRecordPageQueryDO extends BasePageDO<WorkerInfoDO> implements Serializable {

    private static final long serialVersionUID = -8266495121834437512L;

    /**
     * 工人姓名
     */
    private String workerName;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;
}