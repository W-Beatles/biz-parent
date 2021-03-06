package cn.waynechu.springcloud.gateway.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SwaggerResources提供方
 *
 * <pre>
 * Swagger暂不支持webflux项目，所以不能在Gateway配置SwaggerConfig，
 * 可以通过实现SwaggerResourcesProvider接口的方式，来提供SwaggerResources
 * </pre>
 *
 * @author zhuwei
 * @since 2019/4/28 19:29
 */
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    @Resource
    private RouteLocator routeLocator;

    private static final String API_URI = "/v2/api-docs";

    @Value("${gateway.swagger.exclude-applications}")
    private List<String> excludeApplications;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        List<String> routeHosts = new ArrayList<>();
        // 获取所有可用的host:serviceId
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null)
                .filter(route -> !excludeApplications.contains(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务在nacos上
        Set<String> routeHostSet = new HashSet<>();
        routeHosts.forEach(instance -> {
            // 拼接url，样式为/serviceId/v2/api-info，当网关调用这个接口时，会自动通过负载均衡寻找对应的主机
            String url = "/" + instance + API_URI;
            if (!routeHostSet.contains(url)) {
                routeHostSet.add(url);
                SwaggerResource swaggerResource = swaggerResource(instance, url);
                resources.add(swaggerResource);
            }
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
