package com.cloud.mall.fy.orderservice.controller.param;

import lombok.Data;

/**
 * 订单查询参数
 */
@Data
public class OrderQueryParam {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private Integer orderStatus;
}
