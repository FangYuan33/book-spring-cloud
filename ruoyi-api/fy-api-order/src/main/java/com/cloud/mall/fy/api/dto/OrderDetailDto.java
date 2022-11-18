package com.cloud.mall.fy.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单详情页页面Dto
 */
@Data
public class OrderDetailDto implements Serializable {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单价格")
    private Integer totalPrice;

    @ApiModelProperty("订单支付状态码")
    private Integer payStatus;

    @ApiModelProperty("订单支付方式")
    private Integer payType;

    @ApiModelProperty("订单支付方式")
    private String payTypeString;

    @ApiModelProperty("订单支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime payTime;

    @ApiModelProperty("订单状态码")
    private Integer orderStatus;

    @ApiModelProperty("订单状态")
    private String orderStatusString;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("订单详情列表")
    private List<OrderItemDto> orderItemList;
}
