package com.cloud.mall.fy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.OrderItemDto;
import com.cloud.mall.fy.orderservice.entity.OrderItem;

import java.util.List;

public interface OrderItemService extends IService<OrderItem> {

    /**
     * 根据订单号获取明细信息
     */
    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);
}
