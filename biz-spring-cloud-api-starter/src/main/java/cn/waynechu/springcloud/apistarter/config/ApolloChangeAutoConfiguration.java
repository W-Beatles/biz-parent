package cn.waynechu.springcloud.apistarter.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @date 2019/7/11 19:55
 */
@Slf4j
@Configuration
public class ApolloChangeAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @ApolloConfigChangeListener
    private void configChangeListener(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info("Found config change - {}", change.toString());
        }

        // 更新bean的属性值，解决@ConfigurationProperties注解的bean无法刷新问题
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
