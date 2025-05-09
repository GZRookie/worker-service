package com.worker.common.base.tree;

import java.util.List;

/**
 * 树类实现接口
 *
 *  @author
 * @date: 2023/11/6 14:52
 */
public interface TreeConfig<T, IdType> {

    /**
     * 获取子节点
     *
     * @return 子节点列表
     */
    List<T> getChildren();

    /**
     * 获取当前节点ID
     *
     * @return 当前节点ID
     */
    IdType getId();

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    IdType getParentId();
}
