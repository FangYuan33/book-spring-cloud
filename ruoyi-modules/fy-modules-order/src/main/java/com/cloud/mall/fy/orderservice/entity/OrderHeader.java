package com.cloud.mall.fy.orderservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrderHeader extends BaseEntityForMall {

    private String orderNo;

    private Long userId;

    private Integer totalPrice;

    private Integer payStatus;

    private Integer payType;

    private LocalDateTime payTime;

    private Integer orderStatus;

    private String extraInfo;

}