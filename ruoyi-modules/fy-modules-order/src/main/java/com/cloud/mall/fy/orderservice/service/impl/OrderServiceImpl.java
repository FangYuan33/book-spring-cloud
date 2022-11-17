package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.orderservice.dao.OrderMapper;
import com.cloud.mall.fy.orderservice.entity.Order;
import com.cloud.mall.fy.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
