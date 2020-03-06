package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.springcloud.common.util.PathUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 网关鉴权过滤器
 *
 * @author zhuwei
 * @date 2019/4/28 17:29
 */
@Component
@Order(2)
public class AccessGatewayFilter implements GlobalFilter {

    @Value("${spring.cloud.gateway.permit-urls}")
    private List<String> permitUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        String serviceName = route.getUri().getHost();
        String url = "/" + serviceName.toLowerCase() + request.getPath().value();
        if (!PathUtil.antMatch(permitUrls, url)) {
            String authToken = request.getHeaders().getFirst("Authorization");
            if (StringUtil.isEmpty(authToken)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }
        return chain.filter(exchange);
    }
}
