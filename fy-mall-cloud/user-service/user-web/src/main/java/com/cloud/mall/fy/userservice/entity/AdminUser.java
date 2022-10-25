package com.cloud.mall.fy.userservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin_user")
public class AdminUser {
    private Long id;

    private String loginUserName;

    private String loginPassword;

    private String nickName;
}