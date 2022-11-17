package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.orderservice.dao.OrderAddressMapper;
import com.cloud.mall.fy.orderservice.entity.OrderAddress;
import com.cloud.mall.fy.orderservice.service.OrderAddressService;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl extends ServiceImpl<OrderAddressMapper, OrderAddress>
        implements OrderAddressService {

}
