package com.example.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "demo-goods-service", path = "/goods")
public interface DemoGoodsService {

    @GetMapping("/{goodsId}")
    String getGoodsDetail(@PathVariable(value = "goodsId") int goodsId);

    @PostMapping("/delete")
    void delete(Long id);
}
