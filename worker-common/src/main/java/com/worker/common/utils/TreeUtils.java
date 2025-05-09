package com.worker.common.utils;

import com.worker.common.base.tree.TreeConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树状转换工具类
 *
 *  @author
 * @date: 2023/11/6 14:52
 */
public class TreeUtils {

    /**
     * 把集合转化成tree结构
     *
     * @param list     数据集合
     * @param <T>      数据类型
     * @param <IdType> 数据ID的类型
     * @return tree结构集合
     */
    public static <T extends TreeConfig<T, IdType>, IdType> List<T> asTree(List<T> list) {
        int i, l;
        List<T> rootList = new ArrayList<>(4);
        // 以id为key把节点存进去
        Map<IdType, T> treeMap = new HashMap<>(8);
        for (i = 0, l = list.size(); i < l; i++) {
            treeMap.put(list.get(i).getId(), list.get(i));
        }
        for (i = 0, l = list.size(); i < l; i++) {
            // 找到 parent 把子节点add进去
            if (treeMap.containsKey(list.get(i).getParentId()) && (!list.get(i).getId().equals(list.get(i).getParentId()))) {
                treeMap.get(list.get(i).getParentId()).getChildren().add(list.get(i));
            } else {
                // 该节点的parent对象不存在，即为顶层节点
                rootList.add(list.get(i));
            }
        }
        return rootList;

    }
}
