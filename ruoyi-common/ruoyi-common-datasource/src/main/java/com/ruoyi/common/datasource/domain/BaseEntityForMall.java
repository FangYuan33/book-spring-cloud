package com.ruoyi.common.datasource.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntityForMall {

    /**
     * 主键
     */
    protected Long id;

    @TableField(fill = FieldFill.INSERT)
    protected Long createUser;

    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    protected Integer isDeleted;
}
