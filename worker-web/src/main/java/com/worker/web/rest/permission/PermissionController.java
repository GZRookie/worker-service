package com.worker.web.rest.permission;

import com.worker.client.api.permission.PermissionService;
import com.worker.client.response.permisiion.PermissionDTO;
import com.worker.common.base.object.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @GetMapping("/list")
    @ApiOperation(value = "权限列表", notes = "权限页面", httpMethod = "GET")
    public Result<List<PermissionDTO>> listPermission() {
        return permissionService.listPermission();
    }
}
