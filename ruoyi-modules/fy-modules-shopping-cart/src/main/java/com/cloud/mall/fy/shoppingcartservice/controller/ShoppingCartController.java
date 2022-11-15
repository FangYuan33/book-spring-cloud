package com.cloud.mall.fy.shoppingcartservice.controller;

import com.cloud.mall.fy.shoppingcartservice.service.ShoppingCartService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(value = "v1", tags = "新蜂商城购物车相关接口")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

}
