package com.worker.web.rest.worker;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.worker.biz.service.worker.WorkerInfoService;
import com.worker.client.request.worker.WorkerEnableRequest;
import com.worker.client.request.worker.WorkerPageRequest;
import com.worker.client.request.worker.WorkerRequest;
import com.worker.client.response.worker.WorkerInfoDTO;
import com.worker.client.response.worker.WorkerPageDTO;
import com.worker.common.base.object.BasePage;
import com.worker.common.base.object.Result;
import com.worker.common.constant.ResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

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

    @PostMapping("/add")
    @ApiOperation("新增工人信息")
    public Result<Boolean> addWorkerInfo(@RequestBody @Valid WorkerRequest request) {
        // 校验请求类型
        if (Objects.isNull(request.getRequestType()) || request.getRequestType() != 1) {
            return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "请求类型错误");
        }

        boolean result = workerInfoService.addWorkerInfo(request);
        return Result.success(result);
    }

    @PostMapping("/edit")
    @ApiOperation("编辑工人信息")
    public Result<Boolean> editWorkerInfo(@RequestBody @Valid WorkerRequest request) {
        // 校验请求类型
        if (Objects.isNull(request.getRequestType()) || request.getRequestType() != 2) {
            return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "请求类型错误");
        }

        // 校验ID
        if (Objects.isNull(request.getId())) {
            return Result.error(ResponseStatus.PARAM_ERROR.getCode(), "工人ID不能为空");
        }

        boolean result = workerInfoService.editWorkerInfo(request);
        return Result.success(result);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除工人信息")
    public Result<Boolean> deleteWorkerInfo(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return BaseResponse.error(ResponseStatus.PARAM_ERROR.getCode(), "工人ID不能为空");
        }

        boolean result = workerInfoService.deleteWorkerInfo(id);
        return BaseResponse.success(result);
    }

    @PostMapping("/enable")
    @ApiOperation("启用/禁用工人信息")
    public Result<Boolean> enableWorkerInfo(@RequestBody @Valid WorkerEnableRequest request) {
        boolean result = workerInfoService.enableWorkerInfo(request);
        return BaseResponse.success(result);
    }

    @PostMapping("/page")
    @ApiOperation("工人信息分页查询")
    public Result<BasePage<WorkerPageDTO>> pageWorkerInfo(@RequestBody WorkerPageRequest request) {
        IPage<WorkerPageDTO> page = workerInfoService.pageWorkerInfo(request);

        PageResponse<WorkerPageDTO> response = new PageResponse<>();
        response.setList(page.getRecords());
        response.setTotal(page.getTotal());
        response.setPages(page.getPages());

        return BaseResponse.success(response);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("工人信息详情")
    public Result<WorkerInfoDTO> queryWorkerInfoById(@PathVariable("id") Long id) {
        if (Objects.isNull(id)) {
            return BaseResponse.error(ResponseStatus.PARAM_ERROR.getCode(), "工人ID不能为空");
        }

        WorkerInfoDTO dto = workerInfoService.queryWorkerInfoById(id);
        return BaseResponse.success(dto);
    }
}