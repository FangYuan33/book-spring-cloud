package com.cloud.mall.fy.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CarouselDto {
    private Integer carouselId;

    private String carouselUrl;

    private String redirectUrl;

    private Integer carouselRank;

    private Date createTime;

    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;
}