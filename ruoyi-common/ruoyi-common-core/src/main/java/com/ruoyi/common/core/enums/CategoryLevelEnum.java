package com.ruoyi.common.core.enums;

/**
 * 商品分类等级枚举
 */
public enum CategoryLevelEnum {

    LEVEL_ONE(1, "一级分类"),
    LEVEL_TWO(2, "二级分类"),
    LEVEL_THREE(3, "三级分类");

    private final int level;

    private final String name;

    CategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

}
