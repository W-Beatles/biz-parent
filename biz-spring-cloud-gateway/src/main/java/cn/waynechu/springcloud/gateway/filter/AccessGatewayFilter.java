package cn.waynechu.springcloud.gateway.filter;

import cn.waynechu.springcloud.common.util.PathUtil;
import cn.waynechu.springcloud.gateway.enums.AuthTypeEnum;
import cn.waynechu.springcloud.gateway.filter.auth.type.AuthTypeFilter;
import cn.waynechu.springcloud.gateway.property.AuthTypeSwitchProperties;
import cn.waynechu.springcloud.gateway.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
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
@Slf4j
@Component
@Order(2)
public class AccessGatewayFilter implements GlobalFilter {

    @Value("${gateway.permit-urls}")
    private List<String> permitUrls;

    @Autowired
    private AuthTypeSwitchProperties authTypeSwitchProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 放行无需鉴权路由
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return AuthUtil.unauthorized(exchange, "gatewayRoute无效");
        }
        String serviceName = route.getUri().getHost();
        String url = "/" + serviceName.toLowerCase() + request.getPath().value();
        if (PathUtil.antMatch(permitUrls, url)) {
            return chain.filter(exchange);
        }

        // 判断当前authType鉴权渠道是否可用
        AuthTypeEnum authTypeEnum = AuthUtil.getAuthType(request);
        if (authTypeEnum == null) {
            return AuthUtil.unauthorized(exchange, "authType缺失");
        }
        if (!AuthUtil.isAuthTypeOpened(authTypeSwitchProperties, authTypeEnum.getName())) {
            return AuthUtil.unauthorized(exchange, "该authType不可用");
        }

        ApplicationContext applicationContext = exchange.getApplicationContext();
        if (applicationContext == null) {
            return AuthUtil.unauthorized(exchange, "applicationContext无效");
        }

        AuthTypeFilter filter;
        try {
            Object filterObj = applicationContext.getBean(authTypeEnum.getBeanName());
            filter = (AuthTypeFilter) filterObj;
        } catch (NoSuchBeanDefinitionException e) {
            log.error("AuthTypeFilter: {} 未定义", authTypeEnum.getBeanName(), e);
            return AuthUtil.unauthorized(exchange, "该authType未定义");
        }
        return filter.filter(exchange, chain);
    }
}
