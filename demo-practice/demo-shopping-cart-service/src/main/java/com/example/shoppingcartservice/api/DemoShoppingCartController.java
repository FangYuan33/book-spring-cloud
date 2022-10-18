package com.example.shoppingcartservice.api;

import com.example.shoppingcartservice.dao.NewBeeMallShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoShoppingCartController {

    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private NewBeeMallShoppingCartMapper shoppingCartMapper;

    @GetMapping("/shopping-cart/{cartId}")
    public String cartItemDetail(@PathVariable("cartId") int cartId) {
        // 根据id查询商品并返回给调用端
        if (cartId < 0 || cartId > 100000) {
            return "查询购物项为空，当前服务的端口号为" + serverPort;
        }
        String cartItem = "购物项" + cartId;
        // 返回信息给调用端
        return cartItem + "，当前服务的端口号为" + serverPort;
    }

    @PostMapping("/shopping-cart/delete")
    public void delete(@RequestBody Long id) {
        shoppingCartMapper.deleteByPrimaryKey(id);
    }
}
