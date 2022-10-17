package com.example.orderservice.api;

import com.example.orderservice.service.DemoGoodsService;
import com.example.orderservice.service.DemoShoppingCartService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class DemoOrderController {

    @Resource
    private DemoGoodsService demoGoodsService;
    @Resource
    private DemoShoppingCartService demoShoppingCartService;

    @GlobalTransactional
    @GetMapping("/order/saveOrder")
    public String consumerTest(@RequestParam("cartId") int cartId, @RequestParam("goodsId") int goodsId) {
        // 调用商品服务
        String goodsResult = demoGoodsService.getGoodsDetail(goodsId);

        // 调用购物车服务
        String cartResult = demoShoppingCartService.getCartItemDetail(cartId);

        return "success! goodsResult={" + goodsResult + "},cartResult={" + cartResult + "}";
    }
}
