package com.worker.common.base.object;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应
 *
 *  @author
 * @date: 2023/11/5 19:10
 */
@Getter
@Setter
@Builder
@ApiModel(description = "分页")
public class BasePage<T> implements Serializable {
    private static final long serialVersionUID = 6451320399558939681L;

    @ApiModelProperty("数据条数")
    private Long count;

    @ApiModelProperty("数据集合")
    private List<T> data;

    public static <T> BasePage<T> empty() {
        return BasePage.<T>builder()
                .data(Collections.emptyList())
                .count(0L).build();
    }

    public static <T> BasePage<T> build(List<T> data, Long count) {
        return BasePage.<T>builder()
                .data(data)
                .count(count).build();
    }
}
