package com.cloud.mall.fy.orderservice.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 添加收货地址param
 */
@Data
public class UserAddressAddParam {

    @NotEmpty(message = "收件人名称不能为空")
    @ApiModelProperty("收件人名称")
    private String userName;

    @NotEmpty(message = "收件人联系方式不能为空")
    @ApiModelProperty("收件人联系方式")
    private String userPhone;

    @NotNull(message = "是否默认地址不能为空")
    @ApiModelProperty("是否默认地址 0-不是 1-是")
    private Integer defaultFlag;

    @NotEmpty(message = "省不能为空")
    @ApiModelProperty("省")
    private String provinceName;

    @NotEmpty(message = "市不能为空")
    @ApiModelProperty("市")
    private String cityName;

    @NotEmpty(message = "区/县不能为空")
    @ApiModelProperty("区/县")
    private String regionName;

    @NotEmpty(message = "详细地址不能为空")
    @ApiModelProperty("详细地址")
    private String detailAddress;
}
