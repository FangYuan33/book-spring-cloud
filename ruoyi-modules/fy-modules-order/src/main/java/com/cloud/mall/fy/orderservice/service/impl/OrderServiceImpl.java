package com.cloud.mall.fy.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.mall.fy.api.RemoteGoodsService;
import com.cloud.mall.fy.api.RemoteShoppingCartService;
import com.cloud.mall.fy.api.dto.*;
import com.cloud.mall.fy.orderservice.controller.param.OrderPayParam;
import com.cloud.mall.fy.orderservice.controller.param.OrderQueryParam;
import com.cloud.mall.fy.orderservice.controller.param.OrderSaveParam;
import com.cloud.mall.fy.orderservice.dao.OrderMapper;
import com.cloud.mall.fy.orderservice.entity.OrderHeader;
import com.cloud.mall.fy.orderservice.entity.OrderAddress;
import com.cloud.mall.fy.orderservice.entity.OrderItem;
import com.cloud.mall.fy.orderservice.entity.UserAddress;
import com.cloud.mall.fy.orderservice.service.OrderAddressService;
import com.cloud.mall.fy.orderservice.service.OrderItemService;
import com.cloud.mall.fy.orderservice.service.OrderService;
import com.cloud.mall.fy.orderservice.service.UserAddressService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.GoodsSellStatusEnum;
import com.ruoyi.common.core.enums.OrderStatusEnum;
import com.ruoyi.common.core.enums.PayStatusEnum;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.utils.feign.OpenFeignResultUtil;
import com.ruoyi.common.core.utils.uuid.Seq;
import com.ruoyi.common.security.utils.SecurityUtils;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderHeader> implements OrderService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private OrderAddressService orderAddressService;
    @Autowired
    private RemoteShoppingCartService shoppingCartService;
    @Autowired
    private RemoteGoodsService goodsService;

    @Override
    public List<OrderDto> listByCondition(OrderQueryParam orderQueryParam) {
        LambdaQueryWrapper<OrderHeader> queryWrapper = new QueryWrapper<OrderHeader>().lambda()
                .eq(StringUtils.isNotEmpty(orderQueryParam.getOrderNo()), OrderHeader::getOrderNo, orderQueryParam.getOrderNo())
                .eq(orderQueryParam.getOrderStatus() != null, OrderHeader::getOrderStatus, orderQueryParam.getOrderStatus());
        List<OrderHeader> orderHeaders = baseMapper.selectList(queryWrapper);

        return BeanUtils.copyList(orderHeaders, OrderDto.class);
    }

    @Override
    public OrderDetailDto getOrderDetailById(Long orderId) {
        if (orderId == null) {
            throw new ServiceException("参数异常");
        }

        // 获取运单和详情
        OrderHeader orderHeader = baseMapper.selectById(orderId);
        List<OrderItemDto> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

        // 组装返回
        OrderDetailDto result = BeanUtils.copyProperties2(orderHeader, OrderDetailDto.class);
        result.setOrderItemList(orderItems);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @GlobalTransactional
    @Override
    public void saveOrder(OrderSaveParam orderSaveParam) {
        // 参数校验
        if (orderSaveParam.getAddressId() == null || orderSaveParam.getCartItemIds() == null
                || orderSaveParam.getCartItemIds().isEmpty()) {
            throw new ServiceException("参数异常");
        }

        // 拉取购物车数据
        R<List<ShoppingCartItemDto>> shoppingCartResult = shoppingCartService
                .toSettle(orderSaveParam.getCartItemIds(), SecurityConstants.INNER);
        List<ShoppingCartItemDto> shoppingCartItems =
                (List<ShoppingCartItemDto>) OpenFeignResultUtil.processFeignResult(shoppingCartResult);

        // map -> key: goodsId, value: goodsCount
        Map<Long, Integer> goodsCountMap = shoppingCartItems.stream()
                .collect(Collectors.toMap(ShoppingCartItemDto::getGoodsId, ShoppingCartItemDto::getGoodsCount));
        List<Long> goodsIdList = shoppingCartItems.stream().map(ShoppingCartItemDto::getGoodsId).collect(Collectors.toList());

        // 拉取商品数据
        R<List<GoodsDetailDto>> goodsResult = goodsService.getGoodsListById(goodsIdList, SecurityConstants.INNER);
        List<GoodsDetailDto> goodsList = (List<GoodsDetailDto>) OpenFeignResultUtil.processFeignResult(goodsResult);

        // 校验商品库存和上架状态
        checkGoodsStatusAndStock(goodsList, goodsCountMap);

        // 修改库存
        reduceGoodsCount(goodsCountMap);

        // 生成订单
        saveOrder(goodsList, goodsCountMap, orderSaveParam.getAddressId());

        // 删除购物车中商品
        shoppingCartService.deleteShoppingCartItem(orderSaveParam.getCartItemIds(), SecurityConstants.INNER);
    }

    /**
     * 校验商品是否上架和库存是否充足
     */
    private void checkGoodsStatusAndStock(List<GoodsDetailDto> goodsList, Map<Long, Integer> goodsCountMap) {
        for (GoodsDetailDto goodsDetailDto : goodsList) {
            // 校验购物车商品是否已经下架
            if (!GoodsSellStatusEnum.PUT_UP.getValue().equals(goodsDetailDto.getGoodsSellStatus())) {
                throw new ServiceException(goodsDetailDto.getGoodsName() + "已下架，生成订单失败");
            }
            // 判断库存是否充足
            Integer reduceCount = goodsCountMap.get(goodsDetailDto.getId());
            if (goodsDetailDto.getStockNum() < reduceCount) {
                throw new ServiceException(goodsDetailDto.getGoodsName() + "库存不足");
            }
        }
    }

    /**
     * 扣减库存数量
     */
    private void reduceGoodsCount(Map<Long, Integer> goodsCountMap) {
        ArrayList<StockNumDto> stockNumDtoList = new ArrayList<>(goodsCountMap.size());
        for (Map.Entry<Long, Integer> idAndCount : goodsCountMap.entrySet()) {
            stockNumDtoList.add(new StockNumDto(idAndCount.getKey(), idAndCount.getValue()));
        }
        R<Boolean> reduceResult = goodsService.reduceCount(stockNumDtoList, SecurityConstants.INNER);
        if (R.isError(reduceResult)) {
            throw new ServiceException("商品库存扣减失败");
        }
    }

    /**
     * 生成订单
     * 1. 订单头信息
     * 2. 订单明细信息
     * 3. 订单地址信息
     */
    private void saveOrder(List<GoodsDetailDto> goodsList, Map<Long, Integer> goodsCountMap, Long userAddressId) {
        // 初始化订单信息
        OrderHeader orderHeader = initialOrderInfo(goodsList, goodsCountMap);
        if (baseMapper.insert(orderHeader) > 0) {
            // 初始化订单详情信息
            ArrayList<OrderItem> orderItems = initialOrderItemList(goodsList, goodsCountMap, orderHeader.getId());
            orderItemService.saveBatch(orderItems);

            // 初始化订单地址信息
            OrderAddress orderAddress = initialOrderAddress(orderHeader.getId(), userAddressId);
            orderAddressService.save(orderAddress);
        }
    }

    /**
     * 初始化订单头信息
     */
    private OrderHeader initialOrderInfo(List<GoodsDetailDto> goodsList, Map<Long, Integer> goodsCountMap) {
        String orderNum = Seq.getOrderCode();
        int totalPrice = 0;
        for (GoodsDetailDto goodsDetailDto : goodsList) {
            totalPrice += goodsDetailDto.getSellingPrice() * goodsCountMap.get(goodsDetailDto.getId());
        }
        // 订单号、用户ID、总价、待支付状态
        return new OrderHeader().setOrderNo(orderNum).setUserId(SecurityUtils.getUserId()).setTotalPrice(totalPrice)
                .setOrderStatus(OrderStatusEnum.WAIT_PAY.getValue());
    }

    /**
     * 初始化订单详情
     */
    private ArrayList<OrderItem> initialOrderItemList(List<GoodsDetailDto> goodsList, Map<Long, Integer> goodsCountMap,
                                                      Long orderId) {
        ArrayList<OrderItem> orderItems = new ArrayList<>(goodsList.size());
        for (GoodsDetailDto goodsDetailDto : goodsList) {
            // 订单ID、商品ID、商品名、商品图片、商品售价、商品数
            OrderItem orderItem = new OrderItem().setOrderId(orderId).setGoodsId(goodsDetailDto.getId())
                    .setGoodsName(goodsDetailDto.getGoodsName()).setSellingPrice(goodsDetailDto.getSellingPrice())
                    .setGoodsCount(goodsCountMap.get(goodsDetailDto.getId()));
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    /**
     * 初始化订单地址信息
     */
    private OrderAddress initialOrderAddress(Long orderId , Long userAddressId) {
        UserAddress userAddress = userAddressService.getById(userAddressId);
        // 订单ID、收货人电话、姓名、地址信息
        OrderAddress orderAddress = new OrderAddress().setOrderId(orderId);
        BeanUtils.copyProperties(userAddress, orderAddress);

        return orderAddress;
    }

    @Override
    public void batchChangeStatusFromTo(List<Long> idList, OrderStatusEnum from, OrderStatusEnum to) {
        if (!CollectionUtils.isEmpty(idList)) {
            List<OrderHeader> orderHeaders = baseMapper.selectBatchIds(idList);

            // 修改运单状态
            multipleModifyOrderStatus(orderHeaders, from, to);
        }
    }

    @Override
    public void cancelOrderByIda(Long orderId) {
        OrderHeader orderHeader = baseMapper.selectById(orderId);

        // 校验是否是当前用户的订单
        if (!SecurityUtils.getUserId().equals(orderHeader.getUserId())) {
            throw new ServiceException("无操作权限");
        }
        // 状态判断
        if (OrderStatusEnum.DEAL_SUCCESS.getValue().compareTo(orderHeader.getOrderStatus()) <= 0) {
            throw new ServiceException("订单状态无法取消");
        }

        // 修改状态
        multipleModifyOrderStatus(Collections.singletonList(orderHeader), null, OrderStatusEnum.CLOSED_BY_HAND);
    }

    @Override
    public void finishOrder(Long orderId) {
        OrderHeader orderHeader = baseMapper.selectById(orderId);

        // 校验是否是当前用户的订单
        if (!SecurityUtils.getUserId().equals(orderHeader.getUserId())) {
            throw new ServiceException("无操作权限");
        }
        // 状态判断
        if (!OrderStatusEnum.SEND.getValue().equals(orderHeader.getOrderStatus())) {
            throw new ServiceException("订单当前无法确认收货");
        }

        multipleModifyOrderStatus(Collections.singletonList(orderHeader), null, OrderStatusEnum.DEAL_SUCCESS);
    }

    @Override
    public void paySuccess(OrderPayParam orderPayParam) {
        OrderHeader orderHeader = baseMapper.selectById(orderPayParam.getId());

        if (orderHeader != null) {
            // 状态判断
            if (!OrderStatusEnum.WAIT_PAY.getValue().equals(orderHeader.getOrderStatus())) {
                throw new ServiceException("订单状态异常");
            }
            // 支付类型、支付时间、支付状态
            orderHeader.setPayType(orderPayParam.getPayType()).setPayTime(LocalDateTime.now())
                    .setPayStatus(PayStatusEnum.PAY_SUCCESS.getPayStatus());
            // 修改订单状态为已支付
            multipleModifyOrderStatus(Collections.singletonList(orderHeader), OrderStatusEnum.WAIT_PAY, OrderStatusEnum.ALREADY_PAY);
        }
    }

    /**
     * 批量修改运单状态
     *
     * @param from 条件状态，符合该条件则修改可为空，为空时直接修改为目的状态
     * @param to   目的状态
     */
    private void multipleModifyOrderStatus(List<OrderHeader> orderHeaders, OrderStatusEnum from, OrderStatusEnum to) {
        for (OrderHeader orderHeader : orderHeaders) {
            if (from != null && from.getValue().equals(orderHeader.getOrderStatus())) {
                log.info("{} {} -> {}", orderHeader.getOrderNo(), from.getName(), to.getName());
            } else {
                OrderStatusEnum orderStatusEnum = OrderStatusEnum.parse(orderHeader.getOrderStatus());
                log.info("{} {} -> {}", orderHeader.getOrderNo(), orderStatusEnum.getName(), to.getName());
            }
            orderHeader.setOrderStatus(to.getValue());
            baseMapper.updateById(orderHeader);
        }
    }
}
