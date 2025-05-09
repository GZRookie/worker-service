package com.worker.client.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 账号密码登录请求
 *
 *  @author
 * @date: 2023/11/3 11:26
 */
@Getter
@Setter
@ApiModel(description = "账号密码登录请求")
public class PwdLoginRequest implements Serializable {

    private static final long serialVersionUID = -4202383247416396174L;

    @ApiModelProperty(value = "手机号", required = true)
    @Length(min = 11, max = 11, message = "手机号只能是11位")
    @NotEmpty(message = "手机号不能为空")
    private String phoneNum;

    @Length(min = 6, message = "密码应大于6位")
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码")
    private String enterPassword;
}
