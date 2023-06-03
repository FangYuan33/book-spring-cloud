package com.cloud.mall.fy.recommendservice.controller;

import com.cloud.mall.fy.api.dto.CarouselDto;
import com.cloud.mall.fy.api.dto.IndexConfigGoodsDto;
import com.cloud.mall.fy.api.dto.IndexInfoDto;
import com.cloud.mall.fy.recommendservice.service.CarouselService;
import com.cloud.mall.fy.recommendservice.service.IndexConfigService;
import com.ruoyi.common.core.enums.IndexConfigTypeEnum;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "首页接口")
@RequestMapping("/recommend/mall/index")
public class IndexController {

    @Resource
    private CarouselService carouselService;

    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping("/recommendInfos")
    @ApiOperation(value = "获取首页数据", notes = "轮播图、新品、推荐等")
    public AjaxResult indexInfo() {
        IndexInfoDto indexInfoVO = new IndexInfoDto();
        List<CarouselDto> carousels = carouselService.getCarouselsForIndex();
        List<IndexConfigGoodsDto> hotList = indexConfigService
                .getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType());
        List<IndexConfigGoodsDto> newList = indexConfigService
                .getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType());
        List<IndexConfigGoodsDto> recommendList = indexConfigService
                .getConfigGoodsForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMEND.getType());

        indexInfoVO.setCarousels(carousels).setHotGoods(hotList).setNewGoods(newList).setRecommendGoods(recommendList);

        return AjaxResult.success(indexInfoVO);
    }
}
