/**
 * Copyright © 2018 organization waynechu
 * <pre>
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
 * <pre/>
 */
package cn.waynechu.dynamic.datasource.autoconfig;

import cn.waynechu.dynamic.datasource.DynamicDataSourceCreator;
import cn.waynechu.dynamic.datasource.DynamicRoutingDataSource;
import cn.waynechu.dynamic.datasource.autoconfig.druid.DruidDynamicDataSourceConfiguration;
import cn.waynechu.dynamic.datasource.interceptor.DynamicDataSourceInterceptor;
import cn.waynechu.dynamic.datasource.properties.DynamicDataSourceProperties;
import cn.waynechu.dynamic.datasource.provider.DefaultDynamicDataSourceProvider;
import cn.waynechu.dynamic.datasource.provider.DynamicDataSourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author zhuwei
 * @date 2018/11/7 14:28
 */
@Slf4j
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@Import(DruidDynamicDataSourceConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setProvider(dynamicDataSourceProvider);
        dataSource.init();
        return dataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider(DynamicDataSourceCreator dynamicDataSourceCreator) {
        return new DefaultDynamicDataSourceProvider(properties, dynamicDataSourceCreator);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceCreator dynamicDataSourceCreator() {
        DynamicDataSourceCreator dynamicDataSourceCreator = new DynamicDataSourceCreator();
        dynamicDataSourceCreator.setDruidGlobalConfig(properties.getDruid());
        dynamicDataSourceCreator.setHikariGlobalConfig(properties.getHikari());
        return dynamicDataSourceCreator;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}
