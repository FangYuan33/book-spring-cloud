package com.cloud.mall.fy.orderservice.entity;

import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderAddress extends BaseEntityForMall {

    private Long orderId;

    private String userName;

    private String userPhone;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;
}