package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.springcloud.common.util.PathUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * GlobalFilter过滤请求参数
 *
 * @author zhuwei
 * @date 2019/4/28 17:29
 */
@Component
@Order(1)
public class AuthSignatureFilter implements GlobalFilter {

    @Value("${gateway.permit-urls}")
    private List<String> permitUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestUri = exchange.getRequest().getPath().value();
        if (!PathUtil.urlContain(permitUrls, requestUri)) {
            String authToken = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (StringUtil.isEmpty(authToken)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }
}
