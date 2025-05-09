package com.worker.client.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * token响应
 *
 *  @author
 * @date: 2023/11/3 11:23
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "token响应")
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = -2010534769347283930L;

    @ApiModelProperty(value = "token name")
    private String tokenName;

    @ApiModelProperty(value = "token value")
    private String tokenValue;

    @ApiModelProperty("后台用户id")
    private Long adminUserId;
}
