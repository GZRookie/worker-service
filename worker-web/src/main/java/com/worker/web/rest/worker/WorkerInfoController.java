package com.worker.web.rest.worker;

import com.worker.biz.service.worker.WorkerInfoService;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.client.response.worker.WorkerRoleDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 工人信息Controller
 *
 * @date: 2023/11/10 10:30
 */
@RestController
@RequestMapping("/worker/info")
@Api(tags = "工人信息管理")
public class WorkerInfoController {

    @Resource
    private WorkerInfoService workerInfoService;

    @PostMapping("/page")
    @ApiOperation(value = "工人信息分页查询", notes = "工人信息分页查询", httpMethod = "POST")
    public Result<BasePage<WorkerPageDTO>> pageWorkerInfo(@RequestBody WorkerPageRequest request) {
        return workerInfoService.pageWorkerInfo(request);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取员工角色列表", notes = "获取员工角色列表", httpMethod = "GET")
    public Result<List<WorkerRoleDTO>> getWorkerRoleList() {
        return workerInfoService.getWorkerRoleList();
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增工人信息", notes = "新增工人信息", httpMethod = "POST")
    public Result<Boolean> addWorkerInfo(@RequestBody @Valid WorkerRequest request) {
        return workerInfoService.addWorkerInfo(request);
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑工人信息", notes = "编辑工人信息", httpMethod = "POST")
    public Result<Boolean> editWorkerInfo(@RequestBody @Valid WorkerRequest request) {
        return workerInfoService.editWorkerInfo(request);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "工人信息详情", notes = "工人信息详情", httpMethod = "GET")
    public Result<WorkerInfoDTO> queryWorkerInfoById(@PathVariable("id") Long id) {
        return workerInfoService.queryWorkerInfoById(id);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除工人信息", notes = "删除工人信息", httpMethod = "GET")
    public Result<Boolean> deleteWorkerInfo(@PathVariable("id") Long id) {
        return workerInfoService.deleteWorkerInfo(id);
    }

    @PostMapping("/enable")
    @ApiOperation(value = "启用/禁用工人信息", notes = "启用/禁用工人信息", httpMethod = "POST")
    public Result<Boolean> enableWorkerInfo(@RequestBody @Valid WorkerEnableRequest request) {
        return workerInfoService.enableWorkerInfo(request);
    }

    @GetMapping("/export/template")
    @ApiOperation(value = "下载工人信息导入模板", notes = "下载工人信息导入模板", httpMethod = "GET")
    public void downloadImportTemplate(HttpServletResponse response) {
        workerInfoService.downloadImportTemplate(response);
    }

    @PostMapping("/import")
    @ApiOperation(value = "批量导入工人信息", notes = "批量导入工人信息", httpMethod = "POST")
    public Result<String> importWorkerInfo(@RequestParam("file") MultipartFile file) {
        return workerInfoService.importWorkerInfo(file);
    }
}