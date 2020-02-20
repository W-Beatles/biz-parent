package cn.waynechu.springcloud.gateway.swagger;

import cn.waynechu.springcloud.common.util.StringUtil;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
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
@Primary
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    @Resource
    private EurekaClient eurekaClient;

    @Value("#{'${swagger.exclude-applications}'.split(',')}")
    private List<String> excludeApplications;

    @Override
    public List<SwaggerResource> get() {
        List<Application> registeredApplications = eurekaClient.getApplications().getRegisteredApplications();
        List<SwaggerResource> resources = new ArrayList<>();

        registeredApplications.forEach(application -> {
            String applicationName = application.getName().toLowerCase();
            List<InstanceInfo> instances = application.getInstances();

            if (!instances.isEmpty()) {
                String swaggerName = instances.get(0).getMetadata().get("swagger-name");
                if (StringUtil.isNotEmpty(swaggerName)) {
                    resources.add(swaggerResource(swaggerName, "/" + applicationName + "/v2/api-docs"));
                } else if (!excludeApplications.contains(applicationName)) {
                    resources.add(swaggerResource(applicationName, "/" + applicationName + "/v2/api-docs"));
                }
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
