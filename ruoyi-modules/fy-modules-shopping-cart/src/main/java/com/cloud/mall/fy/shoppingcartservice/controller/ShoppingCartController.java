package com.cloud.mall.fy.shoppingcartservice.controller;

import com.cloud.mall.fy.shoppingcartservice.service.ShoppingCartService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(value = "v1", tags = "新蜂商城购物车相关接口")
@RequestMapping("/shopping-cart")
public class ShoppingCartController extends BaseController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping
    @ApiOperation(value = "购物车列表")
    public TableDataInfo cartItemPageList() {
        startPage();
        return getDataTable(shoppingCartService.listByCondition());
    }
}
