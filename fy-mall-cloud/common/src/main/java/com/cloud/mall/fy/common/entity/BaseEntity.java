package com.cloud.mall.fy.common.entity;

import java.time.LocalDateTime;

public class BaseEntity {

    /**
     * 主键
     */
    protected Integer id;

    protected LocalDateTime createTime;

    protected LocalDateTime updateTime;
}
