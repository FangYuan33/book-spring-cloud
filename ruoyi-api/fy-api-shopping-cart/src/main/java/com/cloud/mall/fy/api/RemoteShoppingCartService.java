package com.cloud.mall.fy.api;

import com.cloud.mall.fy.api.dto.ShoppingCartItemDto;
import com.cloud.mall.fy.api.factory.RemoteShoppingCartFallbackFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteShoppingCartService", value = ServiceNameConstants.SHOPPING_CART_SERVICE,
        fallbackFactory = RemoteShoppingCartFallbackFactory.class)
public interface RemoteShoppingCartService {

    @PostMapping("/shopping-cart/inner/listByIds")
    R<List<ShoppingCartItemDto>> toSettle(@RequestBody List<Long> cartItemIds,
                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping("/shopping-cart/inner/delete")
    R<Boolean> deleteShoppingCartItem(@RequestBody List<Long> shoppingCartItemIdList,
                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
