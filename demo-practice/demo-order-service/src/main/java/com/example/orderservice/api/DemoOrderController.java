package com.example.orderservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class DemoOrderController {

    private static final String CLOUD_GOODS_SERVICE_URL = "http://demo-goods-service";

    private static final String CLOUD_SHOPPING_CART_SERVICE_URL = "http://demo-shopping-cart-service";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/order/saveOrder")
    public String consumerTest(@RequestParam("cartId") int cartId, @RequestParam("goodsId") int goodsId) {
        // 调用商品服务
        String goodsResult = restTemplate.getForObject(CLOUD_GOODS_SERVICE_URL + "/goods/" + goodsId, String.class);

        // 调用购物车服务
        String cartResult = restTemplate.getForObject(CLOUD_SHOPPING_CART_SERVICE_URL + "/shop-cart/" + cartId, String.class);

        return "success! goodsResult={" + goodsResult + "},cartResult={" + cartResult + "}";
    }
}
