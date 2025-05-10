package com.worker.biz.constants.worker;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 工人信息接口响应码表
 *
 * @date: 2023/11/8 15:37
 */
@Getter
@Setter
public class WorkerResponseStatus {

    public static final ResponseStatus WORKER_ID_EXIST = new ResponseStatus(5400, "工号已存在");
    public static final ResponseStatus WORKER_ID_IS_NULL = new ResponseStatus(5401, "工人ID不能为空");
    public static final ResponseStatus WORKER_ROLE_ID_IS_NULL = new ResponseStatus(5402, "工人角色ID不能为空");
    public static final ResponseStatus DELETE_PARAM_IS_NULL = new ResponseStatus(5403, "删除参数不能为空");
    public static final ResponseStatus ENABLE_PARAM_IS_NULL = new ResponseStatus(5404, "启用参数不能为空");
    public static final ResponseStatus WORKER_PHONE_NUM_EXIST = new ResponseStatus(5405, "手机号账号已被占用");
    public static final ResponseStatus WORKER_NOT_EXIST = new ResponseStatus(5406, "工人信息不存在");
    public static final ResponseStatus PHONE_NUM_EXIST = new ResponseStatus(5307, "手机号已存在");
    public static final ResponseStatus NOT_WORKER_ROLE = new ResponseStatus(5308, "请选择工人角色类型");
}