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
package cn.waynechu.dynamic.datasource;

import cn.waynechu.dynamic.datasource.autoconfig.druid.DruidConfig;
import cn.waynechu.dynamic.datasource.autoconfig.hikari.HikariCpConfig;
import cn.waynechu.dynamic.datasource.properties.DataSourceProperty;
import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 多数据源创建器
 *
 * @author zhuwei
 * @date 2019/1/15 15:45
 */
@Slf4j
public class DynamicDataSourceCreator {

    /**
     * Druid数据源类名
     */
    private static final String DRUID_DATASOURCE_CLAZZ = "com.alibaba.druid.pool.DruidDataSource";
    /**
     * HikariCp数据源类名
     */
    private static final String HIKARI_DATASOURCE_CLAZZ = "com.zaxxer.hikari.HikariDataSource";
    /**
     * 是否存在Druid依赖
     */
    private Boolean druidExists = false;
    /**
     * 是否存在Hikari依赖
     */
    private Boolean hikariExists = false;

    @Setter
    private DruidConfig druidGlobalConfig;

    @Setter
    private HikariCpConfig hikariGlobalConfig;

    public DynamicDataSourceCreator() {
        try {
            Class.forName(DRUID_DATASOURCE_CLAZZ);
            log.info("检测到Druid依赖，如配置中未指定type，将优先使用Druid连接池");
            druidExists = true;
        } catch (ClassNotFoundException e) {
            // do nothing here.
        }
        try {
            Class.forName(HIKARI_DATASOURCE_CLAZZ);
            hikariExists = true;
        } catch (ClassNotFoundException e) {
            // do nothing here.
        }
    }

    /**
     * 创建数据源
     *
     * @param dataSourceProperty 数据源配置
     * @return 数据源
     */
    public DataSource createDataSource(DataSourceProperty dataSourceProperty) {
        Class<? extends DataSource> type = dataSourceProperty.getType();
        if (type == null) {
            if (druidExists) {
                return createDruidDataSource(dataSourceProperty);
            } else if (hikariExists) {
                return createHikariDataSource(dataSourceProperty);
            }
        } else if (DRUID_DATASOURCE_CLAZZ.equals(type.getName())) {
            return createDruidDataSource(dataSourceProperty);
        } else if (HIKARI_DATASOURCE_CLAZZ.equals(type.getName())) {
            return createHikariDataSource(dataSourceProperty);
        } else {
            log.error("不支持的数据源类型: {}", type.getName());
        }
        return null;
    }

    /**
     * 创建Hikari数据源
     *
     * @param dataSourceProperty 数据源参数
     * @return Hikari数据源
     */
    public DataSource createHikariDataSource(DataSourceProperty dataSourceProperty) {
        HikariCpConfig hikariCpConfig = dataSourceProperty.getHikari();
        HikariConfig config = hikariCpConfig.toHikariConfig(hikariGlobalConfig);
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());
        config.setPoolName(dataSourceProperty.getDataSourceName());
        return new HikariDataSource(config);
    }

    /**
     * 创建Druid数据源
     *
     * @param dataSourceProperty 数据源参数
     * @return Druid数据源
     */
    public DataSource createDruidDataSource(DataSourceProperty dataSourceProperty) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        dataSource.setDriverClassName(dataSourceProperty.getDriverClassName());
        dataSource.setName(dataSourceProperty.getDataSourceName());

        DruidConfig config = dataSourceProperty.getDruid();
        dataSource.configFromPropety(config.toProperties(druidGlobalConfig));
        // 连接参数单独设置
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
