package cn.waynechu.bootstarter.logger;

import cn.waynechu.bootstarter.logger.aware.SentryContextAware;
import cn.waynechu.bootstarter.logger.properties.BannerProperty;
import cn.waynechu.bootstarter.logger.properties.ElkProperty;
import cn.waynechu.bootstarter.logger.properties.SentryProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @date 2019/1/8 13:50
 */
@Configuration
@EnableConfigurationProperties({SentryProperties.class, ElkProperty.class, BannerProperty.class})
public class LoggerAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "sentry.enable", havingValue = "true")
    public SentryContextAware sentryInitializer() {
        return new SentryContextAware();
    }
}
