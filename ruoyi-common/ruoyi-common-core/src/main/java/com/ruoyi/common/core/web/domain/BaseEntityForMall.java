package com.ruoyi.common.core.web.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntityForMall {

    /**
     * 主键
     */
    protected Long id;

    protected Integer createUser;

    protected LocalDateTime createTime;

    protected Integer updateUser;

    protected LocalDateTime updateTime;
}
