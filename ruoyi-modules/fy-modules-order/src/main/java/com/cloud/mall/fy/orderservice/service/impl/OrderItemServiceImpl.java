package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.orderservice.dao.OrderItemMapper;
import com.cloud.mall.fy.orderservice.entity.OrderItem;
import com.cloud.mall.fy.orderservice.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
}
