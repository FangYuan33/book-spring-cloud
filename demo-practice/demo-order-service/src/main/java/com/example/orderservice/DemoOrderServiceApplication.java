package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.orderservice.service")
public class DemoOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoOrderServiceApplication.class, args);
    }

}
