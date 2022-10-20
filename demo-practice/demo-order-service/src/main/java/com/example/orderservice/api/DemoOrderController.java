package com.example.orderservice.api;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.orderservice.service.DemoGoodsService;
import com.example.orderservice.service.DemoShoppingCartService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class DemoOrderController {

    @Resource
    private DemoGoodsService demoGoodsService;
    @Resource
    private DemoShoppingCartService demoShoppingCartService;

    @SentinelResource("testSentinel")
    @GetMapping("/order/saveOrder")
    public String consumerTest(@RequestParam("cartId") int cartId, @RequestParam("goodsId") int goodsId) {
        // 调用商品服务
        String goodsResult = demoGoodsService.getGoodsDetail(goodsId);

        // 调用购物车服务
        String cartResult = demoShoppingCartService.getCartItemDetail(cartId);

        return "success! goodsResult={" + goodsResult + "},cartResult={" + cartResult + "}";
    }

    @GetMapping("/order/break")
    @SentinelResource("testSentinelBreak")
    public String testBreak() throws InterruptedException {
        Thread.sleep(3000);

        return "Success";
    }

    @Transactional
    @GlobalTransactional
    @GetMapping("/order/testSeata")
    public void testSeata(@RequestParam("cartId") long cartId, @RequestParam("goodsId") long goodsId) {
        log.info("Test Seata: cartId:{}, goodsId:{}", cartId, goodsId);

        // 删除商品
        demoGoodsService.delete(goodsId);

        int i = 1 / 0;

        // 删除购物车中商品信息
        demoShoppingCartService.delete(cartId);
    }
}
