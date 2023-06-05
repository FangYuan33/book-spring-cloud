CREATE DATABASE `fy_mall_shopping_cart` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- fy_mall_shopping_cart.shopping_cart_item definition

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

-- fy_mall_shopping_cart.undo_log definition

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