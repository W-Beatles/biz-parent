package cn.waynechu.springcloud.gateway.filter.auth.type.impl;

import cn.waynechu.springcloud.gateway.filter.auth.type.AuthTypeFilter;
import cn.waynechu.springcloud.gateway.util.AuthUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @since 2020-03-07 18:03
 */
@Slf4j
@Component("oauth2AuthTypeFilter")
public class OAuth2AuthTypeFilter implements AuthTypeFilter {

    private static final String BEARER = "bearer ";

    @Value("${oauth2.jwt.signingKey}")
    private String signingKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authentication = request.getHeaders().getFirst("token");
        String method = request.getMethodValue();
        String url = request.getPath().value();

        // 查看用户是否有权限，若有权限进入下一个filter
        if (this.hasPermission(authentication, url, method)) {
            ServerHttpRequest.Builder builder = request.mutate();
            // 将token中的用户信息传给下游服务
            builder.header("user", getUserToken(authentication));
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }
        return AuthUtil.unauthorized(exchange, "无访问权限");
    }

    private String getUserToken(String authentication) {
        String token = "{}";
        try {
            token = new ObjectMapper().writeValueAsString(this.parserJwt(authentication).getBody());
            return token;
        } catch (JsonProcessingException e) {
            log.error("token json error:{}", e.getMessage());
        }
        return token;
    }

    public boolean hasPermission(String authentication, String url, String method) {
        // 如果请求未携带token信息，直接返回无权限
        if (StringUtils.isBlank(authentication) || !authentication.startsWith(BEARER)) {
            return Boolean.FALSE;
        }
        // token是否有效，在网关进行校验，无效/过期等
        if (!isValidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }

        // TODO 2020/9/23 16:00 远程调用认证服务获取是否有权限
        return Boolean.TRUE;
    }

    public boolean isValidJwtAccessToken(String authentication) {
        boolean invalid = Boolean.FALSE;
        try {
            this.parserJwt(authentication);
            invalid = Boolean.TRUE;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException ex) {
            log.error("user token error :{}", ex.getMessage());
        }
        return invalid;
    }

    public Jws<Claims> parserJwt(String jwtToken) {
        if (jwtToken.startsWith(BEARER)) {
            jwtToken = StringUtils.substring(jwtToken, BEARER.length());
        }
        return Jwts.parser().setSigningKey(signingKey.getBytes()).parseClaimsJws(jwtToken);
    }
}
