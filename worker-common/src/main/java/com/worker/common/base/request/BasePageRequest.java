package com.worker.common.base.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 基础分页数据
 *
 *  @author
 * @date: 2023/11/6 15:10
 */
@Getter
@Setter
public class BasePageRequest extends BaseRequest{
    /**
     * 最大页大小
     */
    private static final int MAX_PAGE_SIZE = 1000;

    /**
     * 最大页数
     */
    private static final int MAX_PAGE = 1000000;

    /**
     * 最大排序字段长度
     */
    private static final int MAX_SORT_LENGTH = 100;

    @NotNull(message = "开始页不能为空")
    @ApiModelProperty(value = "页码 从1开始", required = true)
    @Min(value = 1, message = "页码 从1开始")
    @Max(value = MAX_PAGE, message = "页码超过最大限制")
    private Integer offset;

    @NotNull(message = "每页记录数不能为空")
    @ApiModelProperty(value = "每页条数", required = true)
    @Max(value = MAX_SORT_LENGTH, message = "每页记录数超过最大限制")
    private Integer limit;

    @ApiModelProperty(value = "搜索关键字，单个搜索框搜索")
    private String keyWords;

    public BasePageRequest() {
        this.offset = 1;
        this.limit = 10;
    }

    public BasePageRequest(int offset, int limit) {
        this.offset = Math.max(offset, 1);
        this.limit = Math.max(limit, 10);
    }
}
