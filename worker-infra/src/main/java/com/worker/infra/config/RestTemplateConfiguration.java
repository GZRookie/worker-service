package com.worker.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate配置类
 *
 *  @author
 * @date: 2024/2/28 19:11
 */
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
