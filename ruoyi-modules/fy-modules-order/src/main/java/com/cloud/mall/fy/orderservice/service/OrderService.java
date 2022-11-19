package com.cloud.mall.fy.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.OrderDetailDto;
import com.cloud.mall.fy.api.dto.OrderDto;
import com.cloud.mall.fy.orderservice.controller.param.OrderPayParam;
import com.cloud.mall.fy.orderservice.controller.param.OrderQueryParam;
import com.cloud.mall.fy.orderservice.controller.param.OrderSaveParam;
import com.cloud.mall.fy.orderservice.entity.Order;
import com.ruoyi.common.core.enums.OrderStatusEnum;

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
     * 创建订单
     */
    void saveOrder(OrderSaveParam orderSaveParam);

    /**
     * 批量从一个状态切换到另一个状态
     *
     * @param from 条件状态，符合该条件则修改可为空，为空时直接修改为目的状态
     */
    void batchChangeStatusFromTo(List<Long> idList, OrderStatusEnum from, OrderStatusEnum to);

    /**
     * 取消订单
     */
    void cancelOrderByIda(Long orderId);

    /**
     * 确认收货
     */
    void finishOrder(Long orderId);

    /**
     * 支付成功回调
     */
    void paySuccess(OrderPayParam orderPayParam);
}
