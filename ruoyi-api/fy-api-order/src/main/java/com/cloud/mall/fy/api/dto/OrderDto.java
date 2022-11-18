package com.cloud.mall.fy.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private Long id;

    private String orderNo;

    private Long userId;

    private Integer totalPrice;

    private Integer payStatus;

    private Integer payType;

    private LocalDateTime payTime;

    private Integer orderStatus;

    private String extraInfo;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
