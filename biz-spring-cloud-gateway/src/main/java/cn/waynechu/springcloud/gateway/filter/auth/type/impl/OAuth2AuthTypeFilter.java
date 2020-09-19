package cn.waynechu.springcloud.gateway.filter.auth.type.impl;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.gateway.dto.TokenInfo;
import cn.waynechu.springcloud.gateway.filter.auth.type.AuthTypeFilter;
import cn.waynechu.springcloud.gateway.util.AuthUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @since 2020-03-07 18:03
 */
@Slf4j
@Component("oauth2AuthTypeFilter")
public class OAuth2AuthTypeFilter implements AuthTypeFilter {

    // TODO 2020-04-28 23:03 使用WebClient优化接口调用，减少NIO请求
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String authToken = request.getHeaders().getFirst("token");
        if (StringUtil.isEmpty(authToken)) {
            return AuthUtil.unauthorized(exchange, "缺少token信息");
        }

        if (!StringUtils.startsWithIgnoreCase(authToken, "bearer ")) {
            return AuthUtil.unauthorized(exchange, "token无效");
        }

        ServerWebExchange serverWebExchange;
        try {
            /*
            tokenInfo: {"active":true,"aud":["biz-spring-cloud-gateway"],"authorities":["ROLE_ADMIN"],"client_id":"gateway","exp":1583683328,"scope":["read","write"],"user_name":"waynechu"}
            {"error":"invalid_token","error_description":"Token was not recognised"}
             */
            TokenInfo tokenInfo = this.getTokenInfo(authToken);
            if (tokenInfo != null && tokenInfo.isActive()) {
                ServerHttpRequest mutateRequest = request.mutate()
                        .header("user", JSON.toJSONString(tokenInfo)).build();
                serverWebExchange = exchange.mutate().request(mutateRequest).build();
            } else {
                return AuthUtil.unauthorized(exchange, "认证失败");
            }
        } catch (Exception e) {
            log.info("get tokenInfo fail", e);
            return AuthUtil.unauthorized(exchange, "认证失败");
        }
        return chain.filter(serverWebExchange);
    }

    private TokenInfo getTokenInfo(String authToken) {
        String token = StringUtils.substringAfter(authToken, "bearer ");
        String oauthServiceUrl = "http://biz-spring-cloud-oauth-server/oauth/check_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<TokenInfo> response = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
        log.info("tokenInfo: {}", JSON.toJSONString(response.getBody()));
        return response.getBody();
    }
}
