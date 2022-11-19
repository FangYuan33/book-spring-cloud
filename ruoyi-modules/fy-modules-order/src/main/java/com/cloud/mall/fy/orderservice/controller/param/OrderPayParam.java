package com.cloud.mall.fy.orderservice.controller.param;

import lombok.Data;

@Data
public class OrderPayParam {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付方式
     */
    private Integer payType;
}
