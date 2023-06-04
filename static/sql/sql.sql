# 商品表
CREATE DATABASE `fy_mall_goods` DEFAULT CHARACTER SET utf8mb4;

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

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id` BIGINT NOT NULL COMMENT 'branch transaction id',
    `xid` VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context` VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB NOT NULL COMMENT 'rollback info',
    `log_status` INT(11) NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created` DATETIME(6) NOT NULL COMMENT 'create datetime',
    `log_modified` DATETIME(6) NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = 'AT transaction mode undo table';


# 订单表
CREATE DATABASE `fy_mall_order` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `order_header` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
    `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
    `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
    `total_price` int(11) NOT NULL DEFAULT '1' COMMENT '订单总价',
    `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
    `pay_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无 1.支付宝支付 2.微信支付',
    `pay_time` timestamp DEFAULT NULL COMMENT '支付时间',
    `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
    `extra_info` varchar(100) NOT NULL DEFAULT '' COMMENT '订单body',
    `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order_address` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `order_id` bigint(20) NOT NULL,
 `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '收货人姓名',
 `user_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '收货人手机号',
 `province_name` varchar(32) NOT NULL DEFAULT '' COMMENT '省',
 `city_name` varchar(32) NOT NULL DEFAULT '' COMMENT '城',
 `region_name` varchar(32) NOT NULL DEFAULT '' COMMENT '区',
 `detail_address` varchar(64) NOT NULL DEFAULT '' COMMENT '收件详细地址(街道/楼宇/单元)',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
 `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单收货地址关联表';

CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联商品id',
  `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
  `goods_cover_img` varchar(200) NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
  `selling_price` int(11) NOT NULL DEFAULT '1' COMMENT '下单时商品的价格(订单快照)',
  `goods_count` int(11) NOT NULL DEFAULT '1' COMMENT '数量(订单快照)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_address` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
`user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '收货人姓名',
`user_phone` varchar(11) NOT NULL DEFAULT '' COMMENT '收货人手机号',
`default_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为默认 0-非默认 1-是默认',
`province_name` varchar(32) NOT NULL DEFAULT '' COMMENT '省',
`city_name` varchar(32) NOT NULL DEFAULT '' COMMENT '城',
`region_name` varchar(32) NOT NULL DEFAULT '' COMMENT '区',
`detail_address` varchar(64) NOT NULL DEFAULT '' COMMENT '收件详细地址(街道/楼宇/单元)',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
`update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';

# 推荐表
CREATE DATABASE `fy_mall_recommend` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `index_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
`config_name` varchar(50) NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
`config_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
`goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id 默认为0',
`redirect_url` varchar(100) NOT NULL DEFAULT '##' COMMENT '点击后的跳转地址(默认不跳转)',
`config_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
`update_user` int(11) DEFAULT '0' COMMENT '修改者id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO index_config
(config_name, config_type, goods_id, redirect_url, config_rank, is_deleted, create_time, create_user, update_time, update_user)
VALUES
    ('热销商品 iPhone 12', 3, 10906, '##', 201, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('热销商品 华为Mate40 Pro', 3, 10908, '##', 300, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 MackBook2021', 4, 10920, '##', 180, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 华为 P50 Pro', 4, 10921, '##', 160, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 Apple Watch', 4, 10919, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('纪梵希高定香榭天鹅绒唇膏', 5, 10233, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('P50 白色', 5, 10922, '##', 102, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('free buds pro', 5, 10930, '##', 102, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('iPhone 13', 5, 10916, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('华为Mate40 Pro', 5, 10907, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('MacBook Pro 2021', 5, 10920, '##', 100, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('WATCH 3 Pro', 5, 10928, '##', 99, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('塑料浴室座椅', 5, 10154, '##', 80, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('华为 soundx', 5, 10929, '##', 100, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('matepad pro', 5, 10906, '##', 0, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('热销商品 P40', 3, 10902, '##', 200, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 华为 P50 Pocket', 4, 10925, '##', 200, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 华为Mate X Pro', 4, 10926, '##', 200, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('华为 Mate 30 Pro', 5, 10927, '##', 101, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('新品上线 iPhone13', 4, 10915, '##', 190, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0),
    ('Air Pods 第三代', 3, 10918, '##', 301, 0, '2021-03-08 18:55:49', 0, '2021-03-08 18:55:49', 0);

CREATE TABLE `carousel` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
`carousel_url` varchar(100) NOT NULL DEFAULT '' COMMENT '轮播图',
`redirect_url` varchar(100) NOT NULL DEFAULT '''##''' COMMENT '点击后的跳转地址(默认不跳转)',
`carousel_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
`is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
`update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `carousel` (`id`, `carousel_url`, `redirect_url`, `carousel_rank`, `is_deleted`, `create_time`, `create_user`, `update_time`, `update_user`)
VALUES
    (1,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.jpg','##',200,1,'2021-08-23 17:50:45',0,'2021-11-10 00:23:01',0),
    (2,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png','https://juejin.cn/book/7085254558678515742',13,0,'2021-11-29 00:00:00',0,'2021-11-29 00:00:00',0),
    (3,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner3.jpg','##',0,1,'2021-09-18 18:26:38',0,'2021-11-10 00:23:01',0),
    (5,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png','https://juejin.cn/book/7085254558678515742',0,0,'2021-11-29 00:00:00',0,'2021-11-29 00:00:00',0),
    (6,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png','##',101,1,'2021-09-19 23:37:40',0,'2021-11-07 00:15:52',0),
    (7,'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png','##',99,1,'2021-09-19 23:37:58',0,'2021-10-22 00:15:01',0);

# 购物车表
CREATE DATABASE `fy_mall_shopping_cart` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `shopping_cart_item` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
                                      `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
                                      `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联商品id',
                                      `goods_count` int(11) NOT NULL DEFAULT '1' COMMENT '数量(最大为5)',
                                      `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                                      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
                                      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                      `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT       NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(128) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT ='AT transaction mode undo table';