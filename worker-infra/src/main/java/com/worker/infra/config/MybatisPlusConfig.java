package com.worker.infra.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisPlus配置类
 *
 *  @author
 * @date: 2023/11/3 22:14
 */
@Configuration
@MapperScan("com.worker.infra.mapper*")
public class MybatisPlusConfig {

    /**
     * 添加批量插入方法
     */
    @Bean
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }

    /**
     * 分页拦截器
     *
     * @return MP拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
