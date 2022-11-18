package com.cloud.mall.fy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.UserAddressDto;
import com.cloud.mall.fy.orderservice.controller.param.UserAddressAddParam;
import com.cloud.mall.fy.orderservice.controller.param.UserAddressUpdateParam;
import com.cloud.mall.fy.orderservice.entity.UserAddress;

public interface UserAddressService extends IService<UserAddress> {

    /**
     * 保存用户地址
     */
    void saveUserAddress(UserAddressAddParam addressAddParam);

    /**
     * 修改用户地址
     */
    void updateUserAddress(UserAddressUpdateParam addressUpdateParam);

    /**
     * 获取地址信息
     */
    UserAddressDto selectById(Long addressId);

    /**
     * 获取默认地址信息
     */
    UserAddressDto getDefaultUserAddress();
}
