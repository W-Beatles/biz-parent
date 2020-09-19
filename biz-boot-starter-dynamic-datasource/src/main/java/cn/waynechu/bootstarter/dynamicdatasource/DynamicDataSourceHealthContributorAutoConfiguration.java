/**
 * Copyright © 2018 organization waynechu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.waynechu.bootstarter.dynamicdatasource;

import cn.waynechu.bootstarter.dynamicdatasource.dynamic.DynamicRoutingDataSource;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhuwei
 * @since 2020/3/5 18:24
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(JdbcTemplate.class)
@ConditionalOnBean(DataSource.class)
@ConditionalOnEnabledHealthIndicator("dynamic-datasource")
@AutoConfigureBefore(DataSourceHealthContributorAutoConfiguration.class)
public class DynamicDataSourceHealthContributorAutoConfiguration extends CompositeHealthContributorConfiguration<DataSourceHealthIndicator, DataSource> {

    @Bean
    public HealthContributor dbHealthContributor(DynamicRoutingDataSource dynamicRoutingDataSource) {
        Map<String, DataSource> allDataSource = dynamicRoutingDataSource.getAllDataSource();
        return createContributor(allDataSource);
    }
}
