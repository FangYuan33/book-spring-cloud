package com.example.orderservice.api;

import com.example.orderservice.service.DemoGoodsService;
import com.example.orderservice.service.DemoShoppingCartService;
import io.seata.core.exception.TransactionException;
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

    @GetMapping("/order/saveOrder")
    public String consumerTest(@RequestParam("cartId") int cartId, @RequestParam("goodsId") int goodsId) {
        // 调用商品服务
        String goodsResult = demoGoodsService.getGoodsDetail(goodsId);

        // 调用购物车服务
        String cartResult = demoShoppingCartService.getCartItemDetail(cartId);

        return "success! goodsResult={" + goodsResult + "},cartResult={" + cartResult + "}";
    }

    @Transactional
//    @GlobalTransactional
    @GetMapping("/order/testSeata")
    public void testSeata(@RequestParam("cartId") long cartId, @RequestParam("goodsId") long goodsId) throws TransactionException {
        log.info("Test Seata: cartId:{}, goodsId:{}", cartId, goodsId);

        demoGoodsService.delete(goodsId);

        int i = 1 / 0;

        demoShoppingCartService.delete(cartId);
    }
}
