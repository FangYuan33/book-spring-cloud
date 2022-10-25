package com.cloud.mall.fy.userservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.mall.fy.userservice.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper extends BaseMapper<AdminUser> {

    /**
     * 登陆方法
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

}