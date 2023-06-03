package com.cloud.mall.fy.shoppingcartservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.shoppingcartservice.controller.param.SaveCartItemParam;
import com.cloud.mall.fy.shoppingcartservice.controller.param.UpdateCartItemParam;
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

    /**
     * 更新购物车中商品数量
     */
    void updateShoppingCartItem(UpdateCartItemParam updateCartItemParam);

    /**
     * 根据商品ID将其移除购物车
     *
     * @param goodsId 商品ID
     */
    void removeByGoodsId(Long goodsId);

    /**
     * 根据ID获取多条购物车数据
     */
    List<ShoppingCartItemDto> listByItemIds(List<Long> cartItemIds);
}
