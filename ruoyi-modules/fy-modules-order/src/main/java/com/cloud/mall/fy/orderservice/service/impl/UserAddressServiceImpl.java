package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.UserAddressDto;
import com.cloud.mall.fy.orderservice.controller.param.UserAddressAddParam;
import com.cloud.mall.fy.orderservice.controller.param.UserAddressUpdateParam;
import com.cloud.mall.fy.orderservice.dao.UserAddressMapper;
import com.cloud.mall.fy.orderservice.entity.UserAddress;
import com.cloud.mall.fy.orderservice.service.UserAddressService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Override
    public void saveUserAddress(UserAddressAddParam addressAddParam) {
        UserAddress userAddress = BeanUtils.copyProperties2(addressAddParam, UserAddress.class);

        // 默认地址处理
        onlyOneDefaultAddress(userAddress);

        baseMapper.insert(userAddress);
    }

    @Override
    public void updateUserAddress(UserAddressUpdateParam addressUpdateParam) {
        UserAddress userAddress = BeanUtils.copyProperties2(addressUpdateParam, UserAddress.class);

        // 默认地址处理
        onlyOneDefaultAddress(userAddress);

        baseMapper.updateById(userAddress);
    }

    /**
     * 只允许有一条默认地址
     */
    private void onlyOneDefaultAddress(UserAddress userAddress) {
        if (Integer.valueOf(1).equals(userAddress.getDefaultFlag())) {
            UserAddress defaultAddress = getDefaultUserAddressFromDB();
            defaultAddress.setDefaultFlag(0);

            baseMapper.updateById(defaultAddress);
        }
    }

    @Override
    public UserAddressDto selectById(Long addressId) {
        UserAddress userAddress = baseMapper.selectById(addressId);
        return BeanUtils.copyProperties2(userAddress, UserAddressDto.class);
    }

    @Override
    public UserAddressDto getDefaultUserAddress() {
        return BeanUtils.copyProperties2(getDefaultUserAddressFromDB(), UserAddressDto.class);
    }

    /**
     * 查询默认地址
     */
    private UserAddress getDefaultUserAddressFromDB() {
        LambdaQueryWrapper<UserAddress> queryWrapper = new QueryWrapper<UserAddress>().lambda()
                .eq(UserAddress::getUserId, SecurityUtils.getUserId())
                .eq(UserAddress::getDefaultFlag, 1);

        return baseMapper.selectOne(queryWrapper);
    }
}
