package com.worker.web.rest.user;

import com.worker.client.api.user.AdminUserService;
import com.worker.client.response.role.RoleBaseDTO;
import com.worker.client.response.user.AdminUserPageDTO;
import com.worker.client.response.user.AdminUserPermissionInfoDTO;
import com.worker.client.response.user.TokenDTO;
import com.worker.client.request.user.*;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.web.common.anno.CheckOperateAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 后台用户控制器类
 *
 *  @author
 * @date: 2023/11/2 21:56
 */
@RestController
@RequestMapping("/admin/user")
@Api(tags = "用户管理")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/login")
    @ApiOperation(value = "账号密码登陆", notes = "账号密码登录", httpMethod = "POST")
    public Result<TokenDTO> login(@Valid @RequestBody PwdLoginRequest pwdLoginRequest) {
        return adminUserService.login(pwdLoginRequest);
    }

    @PostMapping("/update")
    @ApiOperation(value = "账号密码修改", notes = "账号密码修改", httpMethod = "POST")
    public Result<Boolean> update(@Valid @RequestBody PwdLoginRequest pwdLoginRequest) {
        return adminUserService.update(pwdLoginRequest);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "账号密码登出", notes = "账号密码登出", httpMethod = "POST")
    public Result<Boolean> logout(@Valid @RequestBody PwdLogoutRequest pwdLogoutRequest) {
        return adminUserService.logout(pwdLogoutRequest);
    }

    @GetMapping("/permission")
    @ApiOperation(value = "查询权限", notes = "查询权限", httpMethod = "GET")
    public Result<AdminUserPermissionInfoDTO> queryAdminUserPermissionInfo() {
        return adminUserService.queryAdminUserPermissionInfo();
    }

    @CheckOperateAuth(code = "system:account")
    @PostMapping("/page")
    @ApiOperation(value = "账号页面", notes = "账号页面", httpMethod = "POST")
    public Result<BasePage<AdminUserPageDTO>> pageAdminUser(@Valid @RequestBody AdminUserPageRequest request) {
        return adminUserService.pageAdminUser(request);
    }

    @CheckOperateAuth(code = "system:account")
    @PostMapping("/add")
    @ApiOperation(value = "新增账号", notes = "新增账号", httpMethod = "POST")
    public Result<Boolean> addAdminUser(@Valid @RequestBody AdminUserRequest request) {
        return adminUserService.addAdminUser(request);
    }

    @GetMapping("/show")
    @ApiOperation(value = "编辑账号回显角色", notes = "编辑账号回显角色", httpMethod = "GET")
    public Result<List<RoleBaseDTO>> showAdminUserDetail(@RequestParam(value = "adminUserId") Long adminUserId) {
        return adminUserService.showAdminUserDetail(adminUserId);
    }

    @CheckOperateAuth(code = "system:account")
    @PostMapping("/edit")
    @ApiOperation(value = "编辑账号", notes = "编辑账号", httpMethod = "POST")
    public Result<Boolean> editAdminUser(@Valid @RequestBody AdminUserRequest request) {
        return adminUserService.editAdminUser(request);
    }

    @CheckOperateAuth(code = "system:account")
    @PostMapping("/delete")
    @ApiOperation(value = "删除账号", notes = "删除账号", httpMethod = "POST")
    public Result<Boolean> deleteAdminUser(@Valid @RequestBody AdminUserDeleteRequest request) {
        return adminUserService.deleteAdminUser(request);
    }

    @CheckOperateAuth(code = "system:account")
    @PostMapping("/enable")
    @ApiOperation(value = "启用账号", notes = "启用账号", httpMethod = "POST")
    public Result<Boolean> enableRole(@Valid @RequestBody AdminUserEnableRequest request) {
        return adminUserService.enableAdminUser(request);
    }
}
