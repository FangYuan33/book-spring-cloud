package com.example.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "demo-shopping-cart-service", path = "/shopping-cart")
public interface DemoShoppingCartService {

    @GetMapping("/{cartId}")
    String getCartItemDetail(@PathVariable("cartId") int cartId);

    @PostMapping("/delete")
    void delete(Long id);
}
