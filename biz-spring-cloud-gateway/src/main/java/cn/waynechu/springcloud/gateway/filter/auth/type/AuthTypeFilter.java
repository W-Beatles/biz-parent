package cn.waynechu.springcloud.gateway.filter.auth.type;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权渠道鉴权
 *
 * @author zhuwei
 * @date 2020-03-07 18:02
 */
public interface AuthTypeFilter {

    /**
     * 鉴权
     *
     * @param exchange exchange
     * @param chain    过滤器链
     * @return mono
     */
    Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain);
}
