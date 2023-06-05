CREATE DATABASE `fy_mall_order` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- fy_mall_order.order_address definition

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

-- fy_mall_order.order_header definition

CREATE TABLE `order_header` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
                                `order_no` varchar(20) NOT NULL DEFAULT '' COMMENT '订单号',
                                `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
                                `total_price` int(11) NOT NULL DEFAULT '1' COMMENT '订单总价',
                                `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
                                `pay_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.无 1.支付宝支付 2.微信支付',
                                `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
                                `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
                                `extra_info` varchar(100) NOT NULL DEFAULT '' COMMENT '订单body',
                                `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
                                `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
                                `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改者id',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- fy_mall_order.order_item definition

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

-- fy_mall_order.user_address definition

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

-- fy_mall_order.undo_log definition

CREATE TABLE `undo_log` (
                            `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
                            `xid` varchar(128) NOT NULL COMMENT 'global transaction id',
                            `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
                            `rollback_info` longblob NOT NULL COMMENT 'rollback info',
                            `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
                            `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
                            `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AT transaction mode undo table';