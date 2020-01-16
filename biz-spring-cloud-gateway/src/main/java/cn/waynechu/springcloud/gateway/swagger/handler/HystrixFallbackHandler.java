package cn.waynechu.springcloud.gateway.swagger.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @date 2019/8/16 11:27
 */
@Slf4j
@Component
public class HystrixFallbackHandler implements HandlerFunction<ServerResponse> {

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        log.error("网关执行请求失败: {}, hystrix服务降级处理", serverRequest.uri());
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .contentType(MediaType.TEXT_PLAIN).body(BodyInserters.fromValue("服务异常"));
    }
}
