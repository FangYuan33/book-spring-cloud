# 商品分类表
CREATE TABLE `goods_category`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
    `category_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
    `parent_id`      bigint(20) NOT NULL DEFAULT '0' COMMENT '父分类id',
    `category_name`  varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称',
    `category_rank`  int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
    `is_deleted`     tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
    `create_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_user`    int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
    `update_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_user`    int(11) DEFAULT '0' COMMENT '修改者id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

# 商品信息表
CREATE TABLE `goods_info`
(
    `id`                   bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
    `goods_name`           varchar(200) NOT NULL DEFAULT '' COMMENT '商品名',
    `goods_intro`          varchar(200) NOT NULL DEFAULT '' COMMENT '商品简介',
    `goods_category_id`    bigint(20) NOT NULL DEFAULT '0' COMMENT '关联分类id',
    `goods_cover_img`      varchar(200) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
    `goods_carousel`       varchar(500) NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
    `goods_detail_content` text         NOT NULL COMMENT '商品详情',
    `original_price`       int(11) NOT NULL DEFAULT '1' COMMENT '商品价格',
    `selling_price`        int(11) NOT NULL DEFAULT '1' COMMENT '商品实际售价',
    `stock_num`            int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品库存数量',
    `tag`                  varchar(20)  NOT NULL DEFAULT '' COMMENT '商品标签',
    `goods_sell_status`    tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品上架状态 1-下架 0-上架',
    `create_user`          int(11) NOT NULL DEFAULT '0' COMMENT '添加者主键id',
    `create_time`          datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
    `update_user`          int(11) NOT NULL DEFAULT '0' COMMENT '修改者主键id',
    `update_time`          datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;