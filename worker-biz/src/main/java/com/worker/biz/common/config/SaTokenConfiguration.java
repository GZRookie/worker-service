package com.worker.biz.common.config;

import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sa-token 配置类
 *
 *  @author
 * @date: 2023-11-02 15:46
 */
@Configuration
public class SaTokenConfiguration {
    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}
