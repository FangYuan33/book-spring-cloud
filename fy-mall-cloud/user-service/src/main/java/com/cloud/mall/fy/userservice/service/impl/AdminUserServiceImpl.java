package com.cloud.mall.fy.userservice.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.mall.fy.common.cache.CacheService;
import com.cloud.mall.fy.common.exception.BusinessException;
import com.cloud.mall.fy.common.utils.TokenUtil;
import com.cloud.mall.fy.userservice.controller.param.UpdateAdminParam;
import com.cloud.mall.fy.userservice.dao.AdminUserMapper;
import com.cloud.mall.fy.userservice.entity.AdminUser;
import com.cloud.mall.fy.userservice.service.AdminUserService;
import com.cloud.mall.fy.common.utils.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    /**
     * token存在时间 12H
     */
    private static final Long TOKEN_EXIST_TIME = 43200L;

    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private CacheService cacheService;

    @Override
    public AdminUser login(String userName, String password) {
        AdminUser adminUser = adminUserMapper.login(userName, MD5Util.MD5Encode(password, "UTF-8"));

        if (adminUser != null) {
            // 添加token缓存
            String token = TokenUtil.generateToken(adminUser.getId());
            cacheService.setValue(token, adminUser.getId(), TOKEN_EXIST_TIME);

            return adminUser;
        } else {
            return null;
        }
    }

    @Override
    public AdminUser getUserDetailById(Long loginUserId) {
        return adminUserMapper.selectById(loginUserId);
    }

    @Override
    public void updateAdminUser(UpdateAdminParam param) {
        if (StringUtils.isEmpty(param.getNickName()) && StringUtils.isEmpty(param.getNewPassword())) {
            throw new BusinessException("参数异常");
        }
        if (!StringUtils.isEmpty(param.getNewPassword()) && StringUtils.isEmpty(param.getOriginalPassword())) {
            throw new BusinessException("请输入原始密码");
        }

        doUpdateAdminUser(param);
    }

    private void doUpdateAdminUser(UpdateAdminParam param) {
        UpdateWrapper<AdminUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(!StringUtils.isEmpty(param.getNickName()), AdminUser::getNickName, param.getNickName())
                .set(!StringUtils.isEmpty(param.getNewPassword()) && !StringUtils.isEmpty(param.getOriginalPassword()),
                        AdminUser::getLoginPassword, MD5Util.MD5Encode(param.getNewPassword(), "UTF-8"))
                .eq(!StringUtils.isEmpty(param.getOriginalPassword()),
                        AdminUser::getLoginPassword, MD5Util.MD5Encode(param.getOriginalPassword(), "UTF-8"))
                .eq(AdminUser::getId, param.getId());

        adminUserMapper.update(null, updateWrapper);
    }

    @Override
    public void logout(String token) {
        cacheService.deleteByKey(token);
    }
}
