package com.ruoyi.common.core.enums;

/**
 * 商品上下架状态
 *
 * @author FangYuan
 * @since 2022-11-03 22:04:35
 */
public enum GoodsSellStatusEnum {

    /**
     * 下架
     */
    PUT_DOWN(1, "商品已下架"),
    /**
     * 上架
     */
    PUT_UP(0, "商品已上架");

    private final Integer value;
    private final String desc;

    GoodsSellStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
