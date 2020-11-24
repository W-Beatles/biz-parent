package cn.waynechu.springcloud.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @since 2020/10/9 18:52
 */
@Configuration
public class KeyResolverConfig {

    /**
     * 基于请求url的限流规则
     *
     * @return pathKeyResolver
     */
    @Primary
    @Bean
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    /**
     * 基于IP的限流规则
     *
     * @return remoteAddrKeyResolver
     */
    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
