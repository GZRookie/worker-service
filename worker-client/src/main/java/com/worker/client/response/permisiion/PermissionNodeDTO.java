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
 * 权限响应类
 *
 *  @author
 * @date: 2023/11/5 19:13
 */
@Getter
@Setter
@ToString
@ApiModel(description = "权限响应类")
public class PermissionNodeDTO extends PermissionDTO implements TreeConfig<PermissionNodeDTO, Long>, Serializable {

    private static final long serialVersionUID = 3200864989888320472L;

    /**
     * 为某对象加上children成员变量
     */
    private List<PermissionNodeDTO> children;

    @Override
    public List<PermissionNodeDTO> getChildren() {
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
