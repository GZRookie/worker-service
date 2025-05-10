package com.worker.infra.dataobject.user;

import com.worker.infra.dataobject.BasePageDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 账户分页查询DO
 *
 *  @author
 * @date: 2023/11/9 16:54
 */
@Getter
@Setter
@ToString
public class AdminUserPageQueryDO extends BasePageDO<AdminUserInfoDO> implements Serializable {

    private static final long serialVersionUID = -1796421261805203679L;

    /**
     * 用户名
     */
    private String realName;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 启用状态 0-禁用 1-启用
     */
    private Byte status;

}
