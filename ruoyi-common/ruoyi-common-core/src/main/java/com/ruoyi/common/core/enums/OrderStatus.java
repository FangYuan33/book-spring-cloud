package com.ruoyi.common.core.enums;

import com.ruoyi.common.core.exception.ServiceException;

/**
 * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
 */
public enum OrderStatus {
    WAIT_PAY("待支付", 0),
    ALREADY_PAY("已支付", 1),
    CHECK_DONE("配货完成", 2),
    SEND("出库成功", 3),
    DEAL_SUCCESS("交易成功", 4),
    CLOSED_BY_HAND("手动关闭", -1),
    CLOSED_TIME_TOO_LONG("超时关闭", -2),
    CLOSED_BY_BUSINESS("商家关闭", -3);

    private final String name;

    private final Integer value;

    OrderStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public static OrderStatus parse(Integer value) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.getValue().equals(value)) {
                return orderStatus;
            }
        }
        throw new ServiceException("订单状态异常");
    }
}
