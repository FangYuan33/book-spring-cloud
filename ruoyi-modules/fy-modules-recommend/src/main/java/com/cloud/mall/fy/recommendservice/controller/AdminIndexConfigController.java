package com.cloud.mall.fy.recommendservice.controller;

import com.cloud.mall.fy.api.dto.IndexConfigDto;
import com.cloud.mall.fy.recommendservice.controller.param.IndexConfigAddParam;
import com.cloud.mall.fy.recommendservice.controller.param.IndexConfigEditParam;
import com.cloud.mall.fy.recommendservice.controller.param.IndexConfigQueryParam;
import com.cloud.mall.fy.recommendservice.entity.IndexConfig;
import com.cloud.mall.fy.recommendservice.service.IndexConfigService;
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
@Api(value = "v1", tags = "后台管理系统首页配置模块接口")
@RequestMapping("/indexConfigs/admin")
public class AdminIndexConfigController extends BaseController {

    @Resource
    private IndexConfigService indexConfigService;

    /**
     * 列表
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "首页配置列表", notes = "首页配置列表")
    public TableDataInfo list(IndexConfigQueryParam queryParam) {
        startPage();
        return getDataTable(indexConfigService.list(queryParam));
    }

    /**
     * 添加
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "新增首页配置项", notes = "新增首页配置项")
    public AjaxResult save(@RequestBody @Valid IndexConfigAddParam indexConfigAddParam) {
        IndexConfig indexConfig = BeanUtils.copyProperties2(indexConfigAddParam, IndexConfig.class);
        indexConfigService.saveIndexConfig(indexConfig);

        return AjaxResult.success();
    }

    /**
     * 修改
     */
    @PutMapping(value = "/update")
    @ApiOperation(value = "修改首页配置项", notes = "修改首页配置项")
    public AjaxResult update(@RequestBody @Valid IndexConfigEditParam indexConfigEditParam) {
        IndexConfig indexConfig = BeanUtils.copyProperties2(indexConfigEditParam, IndexConfig.class);
        indexConfig.setId(indexConfigEditParam.getConfigId());

        indexConfigService.updateIndexConfigById(indexConfig);

        return AjaxResult.success();
    }

    /**
     * 详情
     */
    @GetMapping(value = "/detail/{id}")
    @ApiOperation(value = "获取单条首页配置项信息", notes = "根据id查询")
    public AjaxResult info(@PathVariable("id") Long id) {
        IndexConfig indexConfig = indexConfigService.getById(id);

        return AjaxResult.success(BeanUtils.copyProperties2(indexConfig, IndexConfigDto.class));
    }

    /**
     * 删除
     */
    @DeleteMapping(value = "/batchDelete")
    @ApiOperation(value = "批量删除首页配置项信息", notes = "批量删除首页配置项信息")
    public AjaxResult batchDelete(@RequestBody String ids) {
        indexConfigService.batchDelete(ids);

        return AjaxResult.success();
    }
}