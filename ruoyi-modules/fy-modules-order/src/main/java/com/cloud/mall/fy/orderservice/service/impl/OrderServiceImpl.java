package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.OrderDetailDto;
import com.cloud.mall.fy.api.dto.OrderDto;
import com.cloud.mall.fy.api.dto.OrderItemDto;
import com.cloud.mall.fy.orderservice.controller.param.OrderQueryParam;
import com.cloud.mall.fy.orderservice.dao.OrderMapper;
import com.cloud.mall.fy.orderservice.entity.Order;
import com.cloud.mall.fy.orderservice.service.OrderItemService;
import com.cloud.mall.fy.orderservice.service.OrderService;
import com.ruoyi.common.core.enums.OrderStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public List<OrderDto> listByCondition(OrderQueryParam orderQueryParam) {
        LambdaQueryWrapper<Order> queryWrapper = new QueryWrapper<Order>().lambda()
                .eq(StringUtils.isNotEmpty(orderQueryParam.getOrderNo()), Order::getOrderNo, orderQueryParam.getOrderNo())
                .eq(orderQueryParam.getOrderStatus() != null, Order::getOrderStatus, orderQueryParam.getOrderStatus());
        List<Order> orders = baseMapper.selectList(queryWrapper);

        return BeanUtils.copyList(orders, OrderDto.class);
    }

    @Override
    public OrderDetailDto getOrderDetailById(Long orderId) {
        if (orderId == null) {
            throw new ServiceException("参数异常");
        }

        // 获取运单和详情
        Order order = baseMapper.selectById(orderId);
        List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        // 组装返回
        OrderDetailDto result = BeanUtils.copyProperties2(order, OrderDetailDto.class);
        result.setOrderItemList(orderItems);

        return result;
    }

    @Override
    public void batchChangeStatusFromTo(List<Long> idList, OrderStatus from, OrderStatus to) {
        if (!CollectionUtils.isEmpty(idList)) {
            List<Order> orders = baseMapper.selectBatchIds(idList);

            for (Order order : orders) {
                if (from != null && from.getValue().equals(order.getOrderStatus())) {
                    log.info("{} {} -> {}", order.getOrderNo(), from.getName(), to.getName());
                } else {
                    OrderStatus orderStatus = OrderStatus.parse(order.getOrderStatus());
                    log.info("{} {} -> {}", order.getOrderNo(), orderStatus.getName(), to.getName());
                }
                order.setOrderStatus(to.getValue());
                baseMapper.updateById(order);
            }
        }
    }
}
