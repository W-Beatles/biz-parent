package cn.waynechu.bootstarter.dynamicdatasource;

import cn.waynechu.bootstarter.dynamicdatasource.indicator.DynamicDataSourceIndicator;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2020/3/5 18:24
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(DataSource.class)
@ConditionalOnEnabledHealthIndicator("dynamic-datasource")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DynamicDataSourceHealthContributorAutoConfiguration extends CompositeHealthContributorConfiguration<DynamicDataSourceIndicator, DataSource> {

    @Bean
    @ConditionalOnMissingBean(name = "dynamicDataSourceIndicator")
    public HealthContributor rabbitHealthContributor(Map<String, DataSource> dataSourceMap) {
        return createContributor(dataSourceMap);
    }
}
