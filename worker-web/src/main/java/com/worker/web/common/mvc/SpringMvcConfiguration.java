package com.worker.web.common.mvc;

import com.worker.web.common.filter.LogInterceptor;
import com.worker.web.common.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * springmvc配置
 *
 *  @author
 * @date: 2023/11/2 21:27
 */
@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器 多个拦截器时 执行顺序按添加顺序
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/swagger-resources", "/v3/api-docs", "/error");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html", "/swagger-resources", "/v3/api-docs", "/error");
    }
}
