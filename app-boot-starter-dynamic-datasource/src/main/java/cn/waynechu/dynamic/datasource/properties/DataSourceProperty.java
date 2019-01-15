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
package cn.waynechu.dynamic.datasource.properties;

import cn.waynechu.dynamic.datasource.autoconfig.druid.DruidConfig;
import cn.waynechu.dynamic.datasource.autoconfig.hikari.HikariCpConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.sql.DataSource;

/**
 * @author zhuwei
 * @date 2019/1/15 15:49
 */
@Data
@Accessors(chain = true)
public class DataSourceProperty {
    /**
     * 数据源名称，如果需要添加分组以_分割组名和数据源名。如slave_1
     */
    private String dataSourceName;
    /**
     * 连接池类型，如果不配置自动查找 Druid > HikariCp
     */
    private Class<? extends DataSource> type;
    /**
     * JDBC driver
     */
    private String driverClassName;
    /**
     * JDBC url
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;
    /**
     * Druid参数配置
     */
    @NestedConfigurationProperty
    private DruidConfig druid = new DruidConfig();
    /**
     * HikariCp参数配置
     */
    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();
}
