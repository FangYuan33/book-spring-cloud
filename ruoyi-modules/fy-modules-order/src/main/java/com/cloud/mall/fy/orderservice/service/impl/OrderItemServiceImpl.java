package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.OrderItemDto;
import com.cloud.mall.fy.orderservice.dao.OrderItemMapper;
import com.cloud.mall.fy.orderservice.entity.OrderItem;
import com.cloud.mall.fy.orderservice.service.OrderItemService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItem> wrapper = new QueryWrapper<OrderItem>().lambda().eq(OrderItem::getOrderId, orderId);
        List<OrderItem> orderItems = baseMapper.selectList(wrapper);

        return BeanUtils.copyList(orderItems, OrderItemDto.class);
    }
}
