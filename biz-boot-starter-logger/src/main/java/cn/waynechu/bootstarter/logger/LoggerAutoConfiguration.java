package cn.waynechu.bootstarter.logger;

import cn.waynechu.bootstarter.logger.aware.SentryContextAware;
import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.bootstarter.logger.properties.LoggerProperty;
import cn.waynechu.bootstarter.logger.properties.ElkProperty;
import cn.waynechu.bootstarter.logger.properties.SentryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhuwei
 * @date 2019/1/8 13:50
 */
@Configuration
@EnableConfigurationProperties({SentryProperties.class, ElkProperty.class, LoggerProperty.class})
@Import({ApplicationProvider.class})
public class LoggerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = SentryContextAware.SENTRY_ENABLE, havingValue = "true")
    public SentryContextAware sentryInitializer() {
        return new SentryContextAware();
    }
}
