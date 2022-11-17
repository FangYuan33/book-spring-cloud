package com.cloud.mall.fy.orderservice;

import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableRyFeignClients
@EnableCustomSwagger2
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.ruoyi", "com.cloud.mall.fy"})
@MapperScan("com.cloud.mall.fy.orderservice.dao")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
