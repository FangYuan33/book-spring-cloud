package com.cloud.mall.fy.shoppingcartservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.shoppingcartservice.dao.ShoppingCartItemMapper;
import com.cloud.mall.fy.shoppingcartservice.entity.ShoppingCartItem;
import com.cloud.mall.fy.shoppingcartservice.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartItemMapper, ShoppingCartItem>
        implements ShoppingCartService {

}
