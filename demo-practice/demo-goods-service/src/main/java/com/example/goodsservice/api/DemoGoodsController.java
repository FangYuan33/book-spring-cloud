package com.example.goodsservice.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoGoodsController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/goods/{goodsId}")
    public String goodsDetail(@PathVariable("goodsId") int goodsId) {
        // 根据id查询商品并返回给调用端
        if (goodsId < 1 || goodsId > 100000) {
            return "查询商品为空，当前服务的端口号为" + serverPort;
        }
        String goodsName = "商品" + goodsId;
        // 返回信息给调用端
        return goodsName + "，当前服务的端口号为" + serverPort;
    }
}
