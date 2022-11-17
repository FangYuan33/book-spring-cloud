package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.orderservice.dao.UserAddressMapper;
import com.cloud.mall.fy.orderservice.entity.UserAddress;
import com.cloud.mall.fy.orderservice.service.UserAddressService;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

}
