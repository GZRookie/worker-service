package com.worker.web.rest.permission;

import com.worker.client.api.permission.PermissionService;
import com.worker.client.request.permission.PermissionDeleteRequest;
import com.worker.client.request.permission.PermissionPageRequest;
import com.worker.client.request.permission.PermissionRequest;
import com.worker.client.request.permission.PermissionEnableRequest;
import com.worker.client.response.permisiion.PermissionNodeDTO;
import com.worker.common.base.object.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 权限控制器类
 *
 *  @author
 * @date: 2023/11/5 14:25
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/list")
    @ApiOperation(value = "权限页面", notes = "权限页面", httpMethod = "POST")
    public Result<List<PermissionNodeDTO>> listPermission(@Valid @RequestBody PermissionPageRequest request) {
        return permissionService.listPermission(request);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增权限", notes = "新增权限", httpMethod = "POST")
    public Result<Boolean> addPermission(@Valid @RequestBody PermissionRequest request) {
        return permissionService.addPermission(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑权限", notes = "编辑权限", httpMethod = "POST")
    public Result<Boolean> editPermission(@Valid @RequestBody PermissionRequest request) {
        return permissionService.editPermission(request);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除权限", notes = "删除权限", httpMethod = "POST")
    public Result<Boolean> deletePermission(@Valid @RequestBody PermissionDeleteRequest request) {
        return permissionService.deletePermission(request);
    }

    @PostMapping("/enable")
    @ApiOperation(value = "启用权限", notes = "启用权限", httpMethod = "POST")
    public Result<Boolean> enablePermission(@Valid @RequestBody PermissionEnableRequest request) {
        return permissionService.enablePermission(request);
    }
}
