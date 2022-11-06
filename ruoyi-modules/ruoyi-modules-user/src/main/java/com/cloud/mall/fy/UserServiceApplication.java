package com.cloud.mall.fy;

import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableCustomSwagger2
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.ruoyi.common", "com.cloud.mall.fy"})
@MapperScan("com.cloud.mall.fy.userservice.dao")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
