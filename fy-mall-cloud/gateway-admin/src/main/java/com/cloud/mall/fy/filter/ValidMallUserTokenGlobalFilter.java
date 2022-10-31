package com.cloud.mall.fy.filter;

import com.cloud.mall.fy.common.cache.CacheService;
import com.cloud.mall.fy.common.dto.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * 网关层鉴权
 *
 * @author FangYuan
 * @since 2022-10-31 17:13:23
 */
@Component
public class ValidMallUserTokenGlobalFilter implements GlobalFilter {

    @Autowired
    private CacheService cacheService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 跳过鉴权的访问路径: 登录
        List<String> ignoreUrls = Collections.singletonList("/user/admin/login");
        if (ignoreUrls.contains(exchange.getRequest().getURI().getPath())) {
            return chain.filter(exchange);
        }

        // 判断headers非空
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (headers.isEmpty()) {
            return wrapErrorResponse(exchange);
        }

        // 校验token存在
        String token = headers.getFirst("token");
        if (!StringUtils.hasText(token)) {
            return wrapErrorResponse(exchange);
        }
        boolean tokenExist = cacheService.existKey(token);
        if (!tokenExist) {
            return wrapErrorResponse(exchange);
        }

        return chain.filter(exchange);
    }

    /**
     * 封装无权限异常返回结果
     */
    Mono<Void> wrapErrorResponse(ServerWebExchange exchange) {
        Result<Void> result = Result.fail("无权限访问");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultNode = mapper.valueToTree(result);
        byte[] bytes = resultNode.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }
}
