package com.worker.client.response.permisiion;

import com.worker.common.base.tree.TreeConfig;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限基本信息响应类
 *
 *  @author
 * @date: 2023/11/13 0:40
 */
@Getter
@Setter
@ToString
@ApiModel(description = "权限基本信息响应类")
public class PermissionBaseNodeDTO extends PermissionBaseDTO implements TreeConfig<PermissionBaseNodeDTO, Long>, Serializable {
    /**
     * 为某对象加上children成员变量
     */
    private List<PermissionBaseNodeDTO> children;

    @Override
    public List<PermissionBaseNodeDTO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public Long getParentId() {
        return super.getParentId();
    }
}
