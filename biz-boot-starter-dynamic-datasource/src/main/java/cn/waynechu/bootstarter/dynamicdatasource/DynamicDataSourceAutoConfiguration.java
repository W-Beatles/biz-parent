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

import cn.waynechu.bootstarter.dynamicdatasource.aspect.DynamicDataSourceAspect;
import cn.waynechu.bootstarter.dynamicdatasource.config.DruidDynamicDataSourceConfig;
import cn.waynechu.bootstarter.dynamicdatasource.dynamic.AbstractRoutingDataSource;
import cn.waynechu.bootstarter.dynamicdatasource.dynamic.DynamicRoutingDataSource;
import cn.waynechu.bootstarter.dynamicdatasource.interceptor.DynamicDataSourceInterceptor;
import cn.waynechu.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;
import cn.waynechu.bootstarter.dynamicdatasource.provider.DefaultDynamicDataSourceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

/**
 * @author zhuwei
 * @since 2018/11/7 14:28
 */
@Slf4j
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@Import({DruidDynamicDataSourceConfig.class, DynamicDataSourceAspect.class})
public class DynamicDataSourceAutoConfiguration {

    @Autowired
    private DynamicDataSourceProperties properties;

    @Autowired(required = false)
    private ApplicationContext applicationContext;

    @Bean
    @Primary
    @DependsOn("dynamicRoutingDataSource")
    public LazyConnectionDataSourceProxy dataSource(AbstractRoutingDataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRoutingDataSource dynamicRoutingDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setLoggerEnable(properties.isLoggerEnable());
        dynamicRoutingDataSource.setStrategy(properties.getStrategy());
        dynamicRoutingDataSource.setProvider(new DefaultDynamicDataSourceProvider(properties, applicationContext));
        return dynamicRoutingDataSource;
    }

    @Bean
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}