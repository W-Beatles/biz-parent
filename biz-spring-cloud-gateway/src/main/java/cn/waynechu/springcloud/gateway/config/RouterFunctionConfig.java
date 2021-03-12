package cn.waynechu.springcloud.gateway.config;

import cn.waynechu.springcloud.gateway.swagger.handler.HystrixFallbackHandler;
import cn.waynechu.springcloud.gateway.swagger.handler.SwaggerResourceHandler;
import cn.waynechu.springcloud.gateway.swagger.handler.SwaggerSecurityHandler;
import cn.waynechu.springcloud.gateway.swagger.handler.SwaggerUiHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 路由配置
 *
 * @author zhuwei
 * @since 2019/8/16 11:09
 */
@Configuration
@AllArgsConstructor
public class RouterFunctionConfig {

    private HystrixFallbackHandler hystrixFallbackHandler;
    private SwaggerResourceHandler swaggerResourceHandler;
    private SwaggerUiHandler swaggerUiHandler;
    private SwaggerSecurityHandler swaggerSecurityHandler;

    /**
     * 提供运行Swagger-ui需要的一些SwaggerResources端点
     *
     * @return Represents a function that routes to a {@linkplain HandlerFunction handler function}.
     */
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(
                RequestPredicates.path("/fallback")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), hystrixFallbackHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerResourceHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
                .andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
                        .and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
    }
}
