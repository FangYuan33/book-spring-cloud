package com.cloud.mall.fy.recommendservice.controller;

import com.cloud.mall.fy.api.dto.CarouselDto;
import com.cloud.mall.fy.recommendservice.controller.param.CarouselAddParam;
import com.cloud.mall.fy.recommendservice.controller.param.CarouselEditParam;
import com.cloud.mall.fy.recommendservice.entity.Carousel;
import com.cloud.mall.fy.recommendservice.service.CarouselService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
@RestController
@Api(value = "v1", tags = "后台管理系统轮播图模块接口")
@RequestMapping("/recommend/carousels/admin")
public class AdminCarouselController extends BaseController {

    @Resource
    private CarouselService carouselService;

    /**
     * 列表
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "轮播图列表", notes = "轮播图列表")
    public TableDataInfo list() {
        startPage();
        return getDataTable(carouselService.listByCondition());
    }

    /**
     * 添加
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图")
    public AjaxResult save(@RequestBody @Valid CarouselAddParam carouselAddParam) {
        Carousel carousel = BeanUtils.copyProperties2(carouselAddParam, Carousel.class);
        carouselService.save(carousel);

        return AjaxResult.success();
    }

    /**
     * 修改
     */
    @PostMapping(value = "/update")
    @ApiOperation(value = "修改轮播图信息", notes = "修改轮播图信息")
    public AjaxResult update(@RequestBody CarouselEditParam carouselEditParam) {
        Carousel carousel = BeanUtils.copyProperties2(carouselEditParam, Carousel.class);
        carousel.setId(carouselEditParam.getCarouselId());

        carouselService.updateById(carousel);

        return AjaxResult.success();
    }

    /**
     * 详情
     */
    @GetMapping(value = "/detail/{id}")
    @ApiOperation(value = "获取单条轮播图信息", notes = "根据id查询")
    public AjaxResult info(@PathVariable("id") Integer id) {
        Carousel carousel = carouselService.getById(id);
        CarouselDto result = BeanUtils.copyProperties2(carousel, CarouselDto.class);

        return AjaxResult.success(result);
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/batchDelete")
    @ApiOperation(value = "批量删除轮播图信息", notes = "批量删除轮播图信息")
    public AjaxResult batchDelete(@RequestBody String batchIds) {
        carouselService.batchDelete(batchIds);

        return AjaxResult.success();
    }
}