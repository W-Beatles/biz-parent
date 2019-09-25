package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @date 2019/9/24 17:49
 */
@Component
public class RequestIdGenerateFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_ID_KEY = "requestId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String remoteRequestId = exchange.getRequest().getHeaders().getFirst(REQUEST_ID_KEY);
        if (StringUtil.isBlank(remoteRequestId)) {
            remoteRequestId = UUIDUtil.getShortUUID();
        }

        System.out.println(remoteRequestId);
        // 传递requestId
        ServerHttpRequest requestId = exchange.getRequest().mutate().header(REQUEST_ID_KEY, remoteRequestId).build();
        ServerWebExchange build = exchange.mutate().request(requestId).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
