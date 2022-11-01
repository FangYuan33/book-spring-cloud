package com.cloud.mall.fy.goodsservice.service.impl;

import com.cloud.mall.fy.goodsservice.dao.GoodsCategoryMapper;
import com.cloud.mall.fy.goodsservice.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;


}
