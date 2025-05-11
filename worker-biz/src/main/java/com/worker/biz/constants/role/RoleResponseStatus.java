package com.worker.biz.constants.role;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色接口响应码表
 *
 *  @author
 * @date: 2023/11/8 15:37
 */
@Getter
@Setter
public class RoleResponseStatus {

    public static final ResponseStatus NAME_EXIST = new ResponseStatus(5300, "角色名称重复");
    public static final ResponseStatus ROLE_ID_IS_NULL = new ResponseStatus(5301, "角色id不能为空");
    public static final ResponseStatus DELETE_PARAM_IS_NULL = new ResponseStatus(5302, "删除参数不能为空");
    public static final ResponseStatus ENABLE_PARAM_IS_NULL = new ResponseStatus(5303, "启用参数不能为空");
    public static final ResponseStatus ROLE_CANNOT_DELETE = new ResponseStatus(5304, "角色已关联账号，禁止删除");
    public static final ResponseStatus ROLE_IS_NOT_EXIST = new ResponseStatus(5305, "角色不存在");
    public static final ResponseStatus WORKER_ROLE_CANNOT_ADD = new ResponseStatus(5306, "不允许新增工人角色");
    public static final ResponseStatus WORKER_ROLE_CANNOT_EDIT = new ResponseStatus(5307, "工人角色不允许更改");
    public static final ResponseStatus WORKER_ROLE_CANNOT_DELETE = new ResponseStatus(5308, "工人角色不允许删除");
}
