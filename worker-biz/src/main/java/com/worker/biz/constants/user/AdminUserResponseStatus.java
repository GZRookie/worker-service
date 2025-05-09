package com.worker.biz.constants.user;

import com.worker.common.constant.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户接口响应码表
 *
 *  @author
 * @date: 2023/11/3 15:37
 */
@Getter
@Setter
public class AdminUserResponseStatus {
    public static final ResponseStatus LOGIN_ERROR = new ResponseStatus(5100, "登录失败，账号或密码有误");
    public static final ResponseStatus PARAMS_ERROR = new ResponseStatus(5101, "参数错误");
    public static final ResponseStatus PHONE_NUM_FORMAT_ERROR = new ResponseStatus(5102, "手机号必须为11位的纯数字");
    public static final ResponseStatus PASSWORD_FORMAT_ERROR = new ResponseStatus(5103, "密码必须位超过6位的数字或数字加字母组合");
    public static final ResponseStatus ENTER_PASSWORD_FORMAT_ERROR = new ResponseStatus(5104, "确认密码必须位超过6位的数字或数字加字母组合");
    public static final ResponseStatus PHONE_NUM_NOT_EXIST = new ResponseStatus(5105, "手机号不存在");
    public static final ResponseStatus PASSWORD_ERROR = new ResponseStatus(5106, "密码不正确");
    public static final ResponseStatus ENTER_PASSWORD_NOT_EQUAL = new ResponseStatus(5107, "确认密码与密码不一致");
    public static final ResponseStatus ENTER_PASSWORD_IS_EMPTY = new ResponseStatus(5108, "确认密码不能为空");
    public static final ResponseStatus USER_IS_DISABLED = new ResponseStatus(5108, "账号已被禁用，请联系管理员启用账号");
    public static final ResponseStatus USER_NOT_LOGIN = new ResponseStatus(5109, "用户未登录");
    public static final ResponseStatus USER_LOGOUT_FAIL = new ResponseStatus(5110, "用户登出失败");
    public static final ResponseStatus PHONE_NUM_EXIST = new ResponseStatus(5111, "手机号存在");
    public static final ResponseStatus ADMIN_USER_ID_NOT_NULL = new ResponseStatus(5111, "账号id不能为空");

    public static final ResponseStatus DELETE_PARAM_IS_NULL = new ResponseStatus(5112, "删除参数不能为空");
    public static final ResponseStatus ENABLE_PARAM_IS_NULL = new ResponseStatus(5113, "启用参数不能为空");
}
