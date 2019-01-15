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
package cn.waynechu.dynamic.datasource.provider;

import cn.waynechu.dynamic.datasource.DynamicDataSourceCreator;
import cn.waynechu.dynamic.datasource.properties.DataSourceProperty;
import cn.waynechu.dynamic.datasource.properties.DynamicDataSourceProperties;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认动态数据源提供者
 * <p>
 * 默认的从配置文件中加载所有数据源
 *
 * @author zhuwei
 * @date 2019/1/15 17:24
 */
@Slf4j
public class DefaultDynamicDataSourceProvider implements DynamicDataSourceProvider {

    /**
     * 动态数据源创建器
     */
    private DynamicDataSourceCreator dynamicDataSourceCreator;
    /**
     * 动态数据源配置
     */
    private DynamicDataSourceProperties properties;

    public DefaultDynamicDataSourceProvider(DynamicDataSourceProperties properties, DynamicDataSourceCreator dynamicDataSourceCreator) {
        this.properties = properties;
        this.dynamicDataSourceCreator = dynamicDataSourceCreator;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getDatasource();

        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertiesMap.entrySet()) {
            String dataSourceName = entry.getKey();
            DataSourceProperty dataSourceProperty = entry.getValue();
            dataSourceProperty.setDataSourceName(dataSourceName);
            dataSourceMap.put(dataSourceName, dynamicDataSourceCreator.createDataSource(dataSourceProperty));
        }
        return dataSourceMap;
    }
}
