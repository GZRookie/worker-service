package com.worker.web.rest;

import com.worker.common.base.object.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 *
 *  @author
 * @date: 2023/11/3 17:26
 */
@RestController
@RequestMapping("/health")
@Api(tags = "HealthController")
public class HealthController {
    private static final String SUCCESS = "ok";

    @GetMapping("/check")
    @ApiOperation(value = "健康检测", notes = "健康检测", httpMethod = "GET")
    public Result<String> check() {
        return Result.success(SUCCESS);
    }
}
