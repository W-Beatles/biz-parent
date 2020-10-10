package cn.waynechu.springcloud.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author zhuwei
 * @since 2020/10/10 19:17
 */
@Slf4j
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 增加路由
     */
    public void add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 更新路由
     */
    public void update(RouteDefinition definition) {
        // 移除原有路由配置
        try {
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            log.warn("Update fail, not found route. RouteId: {}", definition.getId());
        }
        // 保存新的路由配置
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            log.warn("Update route fail");
        }
    }

    /**
     * 删除路由
     */
    public Mono<ResponseEntity<Object>> delete(String id) {
        return routeDefinitionWriter.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())))
                .onErrorResume(t -> t instanceof NotFoundException, t -> Mono.just(ResponseEntity.notFound().build()));
    }
}
