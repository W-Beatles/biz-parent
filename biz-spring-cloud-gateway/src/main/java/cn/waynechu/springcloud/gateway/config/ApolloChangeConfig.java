package cn.waynechu.springcloud.gateway.config;

import cn.waynechu.springcloud.gateway.service.DynamicRouteService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @since 2019/7/11 19:55
 */
@Slf4j
@Configuration
public class ApolloChangeConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @ApolloConfigChangeListener
    private void configChangeListener(ConfigChangeEvent changeEvent) {
        boolean gatewayPropertiesChanged = false;
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info("Apollo config change - {}", change.toString());

            if (key.startsWith("spring.cloud.gateway")) {
                gatewayPropertiesChanged = true;
            }
        }

        // 更新bean的属性值，解决@ConfigurationProperties注解的bean无法刷新问题
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));

        if (gatewayPropertiesChanged) {
            this.refresherGatewayProperty(changeEvent);
        }
    }

    /**
     * 更新网关路由规则
     */
    private void refresherGatewayProperty(ConfigChangeEvent changeEvent) {
        gatewayProperties.getRoutes().forEach(dynamicRouteService::update);
        log.info("gateway properties refreshed!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
