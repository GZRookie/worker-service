package com.worker.web.rest.role;

import com.worker.client.api.role.RoleService;
import com.worker.client.request.role.RoleDeleteRequest;
import com.worker.client.request.role.RoleEnableRequest;
import com.worker.client.request.role.RolePageRequest;
import com.worker.client.request.role.RoleRequest;
import com.worker.client.response.permisiion.PermissionBaseDTO;
import com.worker.client.response.role.RoleDTO;
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
 * 角色控制器类
 *
 *  @author
 * @date: 2023/11/7 22:18
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    @Resource
    private RoleService roleService;

    @CheckOperateAuth(code = "system:role")
    @PostMapping("/page")
    @ApiOperation(value = "角色页面", notes = "角色页面", httpMethod = "POST")
    public Result<BasePage<RoleDTO>> pageRole(@Valid @RequestBody RolePageRequest request) {
        return roleService.pageRole(request);
    }

    @GetMapping("/list")
    @ApiOperation(value = "角色列表", notes = "角色列表", httpMethod = "GET")
    public Result<List<RoleDTO>> list() {
        return roleService.list();
    }

    @CheckOperateAuth(code = "system:role")
    @PostMapping("/add")
    @ApiOperation(value = "新增角色", notes = "新增角色", httpMethod = "POST")
    public Result<Boolean> addRole(@Valid @RequestBody RoleRequest request) {
        return roleService.addRole(request);
    }

    @GetMapping("/show")
    @ApiOperation(value = "编辑角色回显权限", notes = "编辑角色回显权限", httpMethod = "GET")
    public Result<List<PermissionBaseDTO>> showRoleDetail(@RequestParam(value = "roleId") Long roleId) {
        return roleService.showRoleDetail(roleId);
    }

    @CheckOperateAuth(code = "system:role")
    @PostMapping("/edit")
    @ApiOperation(value = "编辑角色", notes = "编辑角色", httpMethod = "POST")
    public Result<Boolean> editRole(@Valid @RequestBody RoleRequest request) {
        return roleService.editRole(request);
    }

    @CheckOperateAuth(code = "system:role")
    @PostMapping("/delete")
    @ApiOperation(value = "删除角色", notes = "删除角色", httpMethod = "POST")
    public Result<Boolean> deleteRole(@Valid @RequestBody RoleDeleteRequest request) {
        return roleService.deleteRole(request);
    }

    @CheckOperateAuth(code = "system:role")
    @PostMapping("/enable")
    @ApiOperation(value = "启用角色", notes = "启用角色", httpMethod = "POST")
    public Result<Boolean> enableRole(@Valid @RequestBody RoleEnableRequest request) {
        return roleService.enableRole(request);
    }
}
