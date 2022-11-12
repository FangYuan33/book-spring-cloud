package com.cloud.mall.fy.recommendservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.mall.fy.api.dto.IndexConfigDto;
import com.cloud.mall.fy.api.dto.IndexConfigGoodsDto;
import com.cloud.mall.fy.recommendservice.controller.param.IndexConfigQueryParam;
import com.cloud.mall.fy.recommendservice.entity.IndexConfig;

import java.util.List;

public interface IndexConfigService extends IService<IndexConfig> {

    List<IndexConfigDto> list(IndexConfigQueryParam queryParam);

    /**
     * 保存新的热搜配置
     */
    void saveIndexConfig(IndexConfig indexConfig);

    /**
     * 根据ID更新热搜配置
     *
     * @param indexConfig id 必填
     */
    void updateIndexConfigById(IndexConfig indexConfig);

    /**
     * 批量删除
     *
     * @param ids id, 用逗号分割
     */
    void batchDelete(String ids);

    /**
     * 获取首页配置的热搜商品信息
     */
    List<IndexConfigGoodsDto> getConfigGoodsForIndex(Integer configType);
}
