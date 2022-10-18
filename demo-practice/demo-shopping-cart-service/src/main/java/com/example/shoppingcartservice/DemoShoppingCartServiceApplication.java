package com.example.shoppingcartservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.shoppingcartservice.dao")
public class DemoShoppingCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoShoppingCartServiceApplication.class, args);
    }

}
