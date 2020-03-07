package cn.waynechu.springcloud.gateway.filter.auth.type.impl;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.gateway.filter.auth.type.AuthTypeFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @date 2020-03-07 18:03
 */
@Component("oauth2AuthTypeFilter")
public class OAuth2AuthTypeFilter implements AuthTypeFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authToken = request.getHeaders().getFirst("Authorization");
        if (StringUtil.isEmpty(authToken)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }
}
