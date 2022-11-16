package com.cloud.mall.fy.shoppingcartservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.dto.GoodsDetailDto;
import com.cloud.mall.fy.shoppingcartservice.controller.param.SaveCartItemParam;
import com.cloud.mall.fy.shoppingcartservice.dao.ShoppingCartItemMapper;
import com.cloud.mall.fy.shoppingcartservice.entity.ShoppingCartItem;
import com.cloud.mall.fy.shoppingcartservice.service.ShoppingCartService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.mall.fy.api.dto.ShoppingCartItemDto;
import com.cloud.mall.fy.api.RemoteGoodsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.utils.feign.OpenFeignResultUtil.processFeignResult;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartItemMapper, ShoppingCartItem>
        implements ShoppingCartService {

    private static final Long MAX_SHOPPING_CART = 10L;

    @Autowired
    private RemoteGoodsService remoteGoodsService;

    @Override
    public List<ShoppingCartItemDto> listByCondition() {
        List<ShoppingCartItem> shoppingCartItemList = selectListByCondition();

        // 初始化结果参数
        List<ShoppingCartItemDto> result = new ArrayList<>(shoppingCartItemList.size());
        initialShoppingCartItemGoodsInfo(shoppingCartItemList, result);

        return result;
    }

    /**
     * 条件查询购物车列表
     */
    private List<ShoppingCartItem> selectListByCondition() {
        LambdaQueryWrapper<ShoppingCartItem> queryWrapper =
                new QueryWrapper<ShoppingCartItem>().lambda().orderByDesc(ShoppingCartItem::getId);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 初始化购物车DTO列表参数
     *
     * @param shoppingCartItemList 数据库查出来的待连表信息的购物车信息
     * @param result               需要被初始化参数的结果对象
     */
    @SuppressWarnings("unchecked")
    private void initialShoppingCartItemGoodsInfo(List<ShoppingCartItem> shoppingCartItemList,
                                                  List<ShoppingCartItemDto> result) {
        List<Long> goodsIdList = shoppingCartItemList.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
        R<List<GoodsDetailDto>> goodsList = remoteGoodsService.getGoodsListById(goodsIdList, SecurityConstants.INNER);
        List<GoodsDetailDto> goodsDetailDtoList = (List<GoodsDetailDto>) processFeignResult(goodsList);

        // map key: id, value: goodsDetail
        Map<Long, GoodsDetailDto> map = goodsDetailDtoList.stream()
                .collect(Collectors.toMap(GoodsDetailDto::getId, (x) -> x));

        for (ShoppingCartItem shoppingCartItem : shoppingCartItemList) {
            GoodsDetailDto goodsInfo = map.get(shoppingCartItem.getGoodsId());
            // 商品名称、图片、价格、ID
            ShoppingCartItemDto itemDto = BeanUtils.copyProperties2(goodsInfo, ShoppingCartItemDto.class);
            itemDto.setGoodsId(goodsInfo.getId());
            // 购物项ID、商品数量
            itemDto.setCartItemId(shoppingCartItem.getId()).setGoodsCount(shoppingCartItem.getGoodsCount());

            result.add(itemDto);
        }
    }

    @Override
    public void add(SaveCartItemParam saveCartItemParam) {
        // 校验商品必须存在且库存数量足够
        checkGoodsExistAndStock(saveCartItemParam);
        // 校验购物车加购数量
        checkShoppingCartMaxCount();

        ShoppingCartItem cartItem = getSpecificGoodsShoppingCartByGoodsId(saveCartItemParam.getGoodsId());
        // 有则修改
        if (cartItem != null) {
            cartItem.setGoodsCount(saveCartItemParam.getGoodsCount());
            baseMapper.updateById(cartItem);
        } else {
            // 入库
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem().setUserId(SecurityUtils.getUserId())
                    .setGoodsId(saveCartItemParam.getGoodsId()).setGoodsCount(saveCartItemParam.getGoodsCount());
            baseMapper.insert(shoppingCartItem);
        }
    }

    /**
     * 校验商品必须存在且库存数量足够
     */
    private void checkGoodsExistAndStock(SaveCartItemParam saveCartItemParam) {
        R<GoodsDetailDto> goods = remoteGoodsService.getGoodsInfoById(saveCartItemParam.getGoodsId(), SecurityConstants.INNER);
        GoodsDetailDto goodsInfo = (GoodsDetailDto) processFeignResult(goods);

        if (goodsInfo == null) {
            throw new ServiceException("商品不存在");
        }

        if (saveCartItemParam.getGoodsCount() == null || saveCartItemParam.getGoodsCount() < 1) {
            throw new ServiceException("加购数量异常");
        }
        if (saveCartItemParam.getGoodsCount() > goodsInfo.getStockNum()) {
            throw new ServiceException("库存不足");
        }
    }

    /**
     * 校验最大加购数量
     */
    private void checkShoppingCartMaxCount() {
        LambdaQueryWrapper<ShoppingCartItem> countWrapper = new QueryWrapper<ShoppingCartItem>()
                .lambda().eq(ShoppingCartItem::getUserId, SecurityUtils.getUserId());
        Long shoppingCartCount = baseMapper.selectCount(countWrapper);

        if (shoppingCartCount > MAX_SHOPPING_CART) {
            throw new ServiceException("加购数量超出上限");
        }
    }

    /**
     * 校验该用户下是否存在该商品的加购
     */
    private ShoppingCartItem getSpecificGoodsShoppingCartByGoodsId(Long goodsId) {
        LambdaQueryWrapper<ShoppingCartItem> existWrapper = new QueryWrapper<ShoppingCartItem>().lambda()
                .eq(ShoppingCartItem::getUserId, SecurityUtils.getUserId())
                .eq(ShoppingCartItem::getGoodsId, goodsId);

        return baseMapper.selectOne(existWrapper);
    }
}
