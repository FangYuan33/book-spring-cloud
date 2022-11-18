package com.cloud.mall.fy.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收货地址Dto
 */
@Data
public class UserAddressDto {

    @ApiModelProperty("地址id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("收件人名称")
    private String userName;

    @ApiModelProperty("收件人联系方式")
    private String userPhone;

    @ApiModelProperty("是否默认地址 0-不是 1-是")
    private Integer defaultFlag;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("市")
    private String cityName;

    @ApiModelProperty("区/县")
    private String regionName;

    @ApiModelProperty("详细地址")
    private String detailAddress;
}
