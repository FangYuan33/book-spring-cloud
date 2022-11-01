package com.cloud.mall.fy.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    /**
     * 主键
     */
    protected Long id;

    protected Integer createUser;

    protected LocalDateTime createTime;

    protected Integer updateUser;

    protected LocalDateTime updateTime;
}
