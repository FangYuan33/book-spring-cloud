package com.example.goodsservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfiguration {

    /**
     * OpenFeign日志
     */
    @Bean
    public Logger.Level openFeignLogLevel() {
        return Logger.Level.FULL;
    }
}
