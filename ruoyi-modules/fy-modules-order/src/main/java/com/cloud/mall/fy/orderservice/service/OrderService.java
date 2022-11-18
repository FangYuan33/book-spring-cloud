package com.cloud.mall.fy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.OrderDetailDto;
import com.cloud.mall.fy.api.dto.OrderDto;
import com.cloud.mall.fy.orderservice.controller.param.OrderQueryParam;
import com.cloud.mall.fy.orderservice.entity.Order;
import com.ruoyi.common.core.enums.OrderStatus;

import java.util.List;

public interface OrderService extends IService<Order> {

    /**
     * 条件查询运单列表
     */
    List<OrderDto> listByCondition(OrderQueryParam orderQueryParam);

    /**
     * 根据ID获取订单详情
     */
    OrderDetailDto getOrderDetailById(Long orderId);

    /**
     * 批量从一个状态切换到另一个状态
     *
     * @param from 条件状态，符合该条件则修改可为空，为空时直接修改为目的状态
     */
    void batchChangeStatusFromTo(List<Long> idList, OrderStatus from, OrderStatus to);
}
