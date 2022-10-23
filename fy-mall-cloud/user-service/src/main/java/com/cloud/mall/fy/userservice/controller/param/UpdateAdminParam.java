package com.cloud.mall.fy.userservice.controller.param;

import lombok.Data;

@Data
public class UpdateAdminParam {

    private Long id;

    private String nickName;

    /**
     * 原始密码
     */
    private String originalPassword;

    private String newPassword;
}
