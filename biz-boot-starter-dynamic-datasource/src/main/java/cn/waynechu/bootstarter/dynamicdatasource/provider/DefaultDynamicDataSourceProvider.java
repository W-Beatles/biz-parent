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
package cn.waynechu.bootstarter.dynamicdatasource.provider;

import cn.waynechu.bootstarter.dynamicdatasource.autoconfig.DruidConfig;
import cn.waynechu.bootstarter.dynamicdatasource.properties.DataSourceProperty;
import cn.waynechu.bootstarter.dynamicdatasource.properties.DynamicDataSourceProperties;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
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
     * 动态数据源配置
     */
    private DynamicDataSourceProperties properties;

    public DefaultDynamicDataSourceProvider(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getDatasource();
        DruidConfig druidGlobalConfig = properties.getDruid();

        Map<String, DataSource> dataSourceMap = new LinkedHashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> entry : dataSourcePropertiesMap.entrySet()) {
            String dataSourceName = entry.getKey();
            DataSourceProperty dataSourceProperty = entry.getValue();
            dataSourceProperty.setDataSourceName(dataSourceName);
            dataSourceMap.put(dataSourceName, createDataSource(dataSourceProperty, druidGlobalConfig));
        }
        return dataSourceMap;
    }

    /**
     * 创建Druid数据源
     *
     * @param dataSourceProperty 数据源参数
     * @return Druid数据源
     */
    public static DataSource createDataSource(DataSourceProperty dataSourceProperty, DruidConfig druidGlobalConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getDataSourceName());

        DruidConfig config = dataSourceProperty.getDruid();
        dataSource.configFromPropety(config.toProperties(druidGlobalConfig));
        // connectProperties连接参数单独设置
        dataSource.setConnectProperties(config.getConnectionProperties());
        // 设置druid内置properties不支持的的参数
        Boolean testOnReturn = config.getTestOnReturn() == null ? druidGlobalConfig.getTestOnReturn() : config.getTestOnReturn();
        if (testOnReturn != null && testOnReturn.equals(true)) {
            dataSource.setTestOnReturn(true);
        }
        Integer validationQueryTimeout = config.getValidationQueryTimeout() == null ? druidGlobalConfig.getValidationQueryTimeout() : config.getValidationQueryTimeout();
        if (validationQueryTimeout != null && !validationQueryTimeout.equals(-1)) {
            dataSource.setValidationQueryTimeout(validationQueryTimeout);
        }
        Boolean sharePreparedStatements = config.getSharePreparedStatements() == null ? druidGlobalConfig.getSharePreparedStatements() : config.getSharePreparedStatements();
        if (sharePreparedStatements != null && sharePreparedStatements.equals(true)) {
            dataSource.setSharePreparedStatements(true);
        }
        Integer connectionErrorRetryAttempts = config.getConnectionErrorRetryAttempts() == null ? druidGlobalConfig.getConnectionErrorRetryAttempts() : config.getConnectionErrorRetryAttempts();
        if (connectionErrorRetryAttempts != null && !connectionErrorRetryAttempts.equals(1)) {
            dataSource.setConnectionErrorRetryAttempts(connectionErrorRetryAttempts);
        }
        Boolean breakAfterAcquireFailure = config.getBreakAfterAcquireFailure() == null ? druidGlobalConfig.getBreakAfterAcquireFailure() : config.getBreakAfterAcquireFailure();
        if (breakAfterAcquireFailure != null && breakAfterAcquireFailure.equals(true)) {
            dataSource.setBreakAfterAcquireFailure(true);
        }
        try {
            dataSource.init();
        } catch (SQLException e) {
            log.error("Druid数据源创建失败", e);
        }
        return dataSource;
    }
}
