package com.example.goodsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.goodsservice.dao")
public class DemoGoodsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGoodsServiceApplication.class, args);
    }

}
