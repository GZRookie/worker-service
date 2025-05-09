package com.worker.biz.constants.permission;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限接口响应码表
 *
 *  @author
 * @date: 2023/11/6 17:45
 */
@Getter
@Setter
public class PermissionResponseStatus {

    public static final ResponseStatus PERMISSION_ID_IS_NULL = new ResponseStatus(5200, "id不能为空");
    public static final ResponseStatus NAME_EXIST = new ResponseStatus(5201, "菜单名称重复");
    public static final ResponseStatus DELETE_PARAM_IS_NULL = new ResponseStatus(5202, "删除参数不能为空");
    public static final ResponseStatus ENABLE_PARAM_IS_NULL = new ResponseStatus(5203, "启用参数不能为空");
    public static final ResponseStatus PARENT_ID_NOT_EXIST = new ResponseStatus(5204, "上级目录/菜单不存在，请选择正确的目录/菜单");
    public static final ResponseStatus CANNOT_CHOSE_DIRECTORY = new ResponseStatus(5205, "目录不能选择上级目录");
    public static final ResponseStatus PARENT_ID_IS_NOT_NULL = new ResponseStatus(5206, "上级目录不能为空，请选择上级目录");
    public static final ResponseStatus CANNOT_CHOSE_MENU = new ResponseStatus(5207, "上级目录不能是菜单，请选择目录");
}
