package com.worker.biz.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JWT配置
 *
 *  @author
 * @date: 2023/11/4 1:01
 */
@Data
@Component
@ConfigurationProperties(prefix = "worker.jwt")
public class JwtProperties {
    /**
     * 失效时间
     */
    public static final String EFF = "eff";

    /**
     * 是否开启JWT，即注入相关的类对象
     */
    private Boolean enabled;

    /**
     * JWT密钥
     */
    private String secret;

    /**
     * 前端向后端传递JWT时使用HTTP的header名称
     */
    private String header;

    /**
     * 用户获取JWT令牌发送的用户名参数名称
     */
    private String adminUserIdKey = "adminUserId";

    /**
     * 用户获取JWT令牌发送的用户名参数名称
     */
    private String frontUserIdKey = "frontUserId";

    /**
     * 用户获取JWT令牌发送的用户名参数名称
     */
    private String phoneNumKey = "phoneNum";

    /**
     * 登录类型
     */
    private String loginType = "login";

    /**
     * 权限全面开放的接口，不需要JWT令牌就可以访问
     */
    private List<String> permitMethods;

    /**
     * 未开放的api接口
     */
    private List<String> unopenedApis;
}
