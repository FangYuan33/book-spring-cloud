package com.cloud.mall.fy.recommendservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.RemoteGoodsService;
import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.api.dto.IndexConfigDto;
import com.cloud.mall.fy.api.dto.IndexConfigGoodsDto;
import com.cloud.mall.fy.recommendservice.controller.param.IndexConfigQueryParam;
import com.cloud.mall.fy.recommendservice.dao.IndexConfigMapper;
import com.cloud.mall.fy.recommendservice.entity.IndexConfig;
import com.cloud.mall.fy.recommendservice.service.IndexConfigService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.utils.feign.OpenFeignResultUtil.processFeignResult;

@Service
public class IndexConfigServiceImpl extends ServiceImpl<IndexConfigMapper, IndexConfig> implements IndexConfigService {

    @Autowired
    private RemoteGoodsService remoteGoodsService;

    @Override
    public List<IndexConfigDto> list(IndexConfigQueryParam queryParam) {
        LambdaQueryWrapper<IndexConfig> queryWrapper = new QueryWrapper<IndexConfig>().lambda()
                .like(queryParam.getConfigType() != null, IndexConfig::getConfigType, queryParam.getConfigType());
        List<IndexConfig> configList = baseMapper.selectList(queryWrapper);

        return BeanUtils.copyList(configList, IndexConfigDto.class);
    }

    @Override
    public void saveIndexConfig(IndexConfig indexConfig) {
        // 校验要绑定的商品是否存在
        if (checkExistGoodsDetailById(indexConfig.getGoodsId())) {
            // 判断是否已存在配置
            if (existSameConfig(indexConfig.getGoodsId(), indexConfig.getConfigType())) {
                throw new ServiceException("该商品推荐配置已存在");
            } else {
                baseMapper.insert(indexConfig);
            }
        }
    }

    @Override
    public void updateIndexConfigById(IndexConfig indexConfig) {
        if (indexConfig.getId() == null) {
            throw new ServiceException("参数异常");
        }

        // 校验要绑定的商品是否存在
        if (checkExistGoodsDetailById(indexConfig.getGoodsId())) {
            if (existSameConfig(indexConfig.getGoodsId(), indexConfig.getConfigType())) {
                throw new ServiceException("该商品推荐配置已存在");
            } else {
                baseMapper.updateById(indexConfig);
            }
        }
    }

    /**
     * 校验要绑定的商品是否存在
     *
     * @param goodsId 商品ID
     */
    private boolean checkExistGoodsDetailById(Long goodsId) {
        R<GoodsDetailDto> result = remoteGoodsService.getGoodsInfoById(goodsId, SecurityConstants.INNER);
        GoodsDetailDto goodsDetailDto = (GoodsDetailDto) processFeignResult(result);

        return goodsDetailDto != null;
    }

    /**
     * 是否已经存在同一个商品同样的配置
     *
     * @param goodsId    商品ID
     * @param configType 配置类型
     */
    private boolean existSameConfig(Long goodsId, Integer configType) {
        LambdaQueryWrapper<IndexConfig> queryWrapper = new QueryWrapper<IndexConfig>().lambda()
                .eq(IndexConfig::getGoodsId, goodsId).eq(IndexConfig::getConfigType, configType);

        return baseMapper.exists(queryWrapper);
    }

    @Override
    public void batchDelete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new ServiceException("参数异常");
        }

        String[] idArray = ids.split(",");
        baseMapper.deleteBatchIds(Arrays.asList(idArray));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IndexConfigGoodsDto> getConfigGoodsForIndex(Integer configType) {
        LambdaQueryWrapper<IndexConfig> queryWrapper = new QueryWrapper<IndexConfig>().lambda()
                .eq(IndexConfig::getConfigType, configType);
        List<IndexConfig> indexConfigs = baseMapper.selectList(queryWrapper);

        if (!Collections.isEmpty(indexConfigs)) {
            List<Long> goodsIdList = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());

            // 获取商品明细列表
            R<List<GoodsDetailDto>> result = remoteGoodsService.getGoodsListById(goodsIdList, SecurityConstants.INNER);
            List<GoodsDetailDto> goodsDetailDtoList = (List<GoodsDetailDto>) processFeignResult(result);
            // 转换成首页商品信息
            return BeanUtils.copyList(goodsDetailDtoList, IndexConfigGoodsDto.class);
        }

        return java.util.Collections.emptyList();
    }
}
