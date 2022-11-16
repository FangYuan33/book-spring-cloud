package com.cloud.mall.fy.shoppingcartservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.shoppingcartservice.controller.param.SaveCartItemParam;
import com.cloud.mall.fy.shoppingcartservice.entity.ShoppingCartItem;
import com.cloud.mall.fy.api.dto.ShoppingCartItemDto;

import java.util.List;

/**
 * 购物车服务层
 */
public interface ShoppingCartService extends IService<ShoppingCartItem> {

    /**
     * 购物车列表页
     */
    List<ShoppingCartItemDto> listByCondition();

    /**
     * 添加商品到购物车
     */
    void add(SaveCartItemParam saveCartItemParam);
}
