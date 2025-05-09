package com.worker.infra.dataobject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页参数DO
 *
 *  @author
 * @date: 2023/11/9 16:58
 */
@Getter
@Setter
@ToString
public class BasePageDO<T> {

    private static final long serialVersionUID = 4778577597784786766L;

    /**
     * 页码
     */
    private Integer offset;

    /**
     * 条数
     */
    private Integer limit;

    /**
     * 搜索关键字
     */
    private String keyWords;

    public BasePageDO() {
        this.offset = 1;
        this.limit = 10;
    }

    public BasePageDO(int offset, int limit) {
        this.offset = Math.max(offset, 1);
        this.limit = Math.max(limit, 10);
    }
}
