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
package cn.waynechu.bootstarter.dynamicdatasource.properties;

import cn.waynechu.bootstarter.dynamicdatasource.config.DruidConfig;
import cn.waynechu.bootstarter.dynamicdatasource.strategy.DynamicDataSourceStrategy;
import cn.waynechu.bootstarter.dynamicdatasource.strategy.RoundRobinDynamicDataSourceStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态数据源配置
 *
 * @author zhuwei
 * @since 2019/1/15 16:42
 */
@Data
@ConfigurationProperties(prefix = DynamicDataSourceProperties.DYNAMIC_DATA_SOURCE_PREFIX)
public class DynamicDataSourceProperties {
    public static final String DYNAMIC_DATA_SOURCE_PREFIX = "spring.datasource.dynamic";

    /**
     * 打印动态数据源执行日志
     */
    private boolean loggerEnable = false;

    /**
     * 配置所有数据源
     */
    private Map<String, DataSourceProperty> datasource = new LinkedHashMap<>();

    /**
     * 动态数据源选择策略，默认轮询策略
     */
    private Class<? extends DynamicDataSourceStrategy> strategy = RoundRobinDynamicDataSourceStrategy.class;

    /**
     * Druid全局参数配置
     */
    @NestedConfigurationProperty
    private DruidConfig druid = new DruidConfig();
}
