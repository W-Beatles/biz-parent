package cn.waynechu.springcloud.gateway.swagger;

import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerResources提供方
 *
 * <pre>
 * Swagger暂不支持webflux项目，所以不能在Gateway配置SwaggerConfig，
 * 可以通过实现SwaggerResourcesProvider接口的方式，来提供SwaggerResources
 * </pre>
 *
 * @author zhuwei
 * @date 2019/4/28 19:29
 */
@Component
@Primary
public class GatewaySwaggerProvider implements SwaggerResourcesProvider {
    private static final String API_URI = "/v2/api-docs";
    private static final String EUREKA_SUB_PREFIX = "CompositeDiscoveryClient_";

    private final DiscoveryClientRouteDefinitionLocator routeLocator;

    public GatewaySwaggerProvider(DiscoveryClientRouteDefinitionLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        // 从DiscoveryClientRouteDefinitionLocator 中取出routes，构造成swaggerResource
        routeLocator.getRouteDefinitions().subscribe(routeDefinition ->
        {
            resources.add(swaggerResource(
                    routeDefinition.getId().substring(EUREKA_SUB_PREFIX.length()),
                    routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI)));
        });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
