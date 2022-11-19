package com.cloud.mall.fy.orderservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.datasource.domain.BaseEntityForMall;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrderAddress extends BaseEntityForMall {

    private Long orderId;

    private String userName;

    private String userPhone;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    @TableField(exist = false)
    private Integer isDeleted;

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}