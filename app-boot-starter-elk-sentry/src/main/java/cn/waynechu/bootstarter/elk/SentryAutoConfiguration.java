package cn.waynechu.bootstarter.elk;

import cn.waynechu.bootstarter.elk.initializer.SentryInitializer;
import cn.waynechu.bootstarter.elk.properties.SentryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @date 2019/1/8 13:50
 */
@Configuration
@EnableConfigurationProperties({SentryProperties.class})
public class SentryAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "sentry.enable", havingValue = "true")
    public SentryInitializer sentryInitializer(SentryProperties sentryProperties) {
        return new SentryInitializer(sentryProperties);
    }
}
