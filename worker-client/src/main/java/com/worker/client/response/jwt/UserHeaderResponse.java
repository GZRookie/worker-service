package com.worker.client.response.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JWT响应
 *
 *  @author
 * @date: 2023/11/4 0:56
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserHeaderResponse implements Serializable {

    private static final long serialVersionUID = 5995101690198347111L;

    /**
     * 后台用户Id
     */
    private String adminUserId;

    /**
     * 后台用户Id
     */
    private String frontUserId;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * token失效时间
     */
    private String eff;
}
