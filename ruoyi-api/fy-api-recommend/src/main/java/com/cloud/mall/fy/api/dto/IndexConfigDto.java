package com.cloud.mall.fy.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class IndexConfigDto {
    private Long configId;

    private String configName;

    private Integer configType;

    private Long goodsId;

    private String redirectUrl;

    private Integer configRank;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;
}