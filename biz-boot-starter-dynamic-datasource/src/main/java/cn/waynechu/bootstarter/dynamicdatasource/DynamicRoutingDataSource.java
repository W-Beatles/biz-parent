/**
 * Copyright © 2018 organization waynechu
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.waynechu.bootstarter.dynamicdatasource;

import cn.waynechu.bootstarter.dynamicdatasource.provider.DynamicDataSourceProvider;
import cn.waynechu.bootstarter.dynamicdatasource.strategy.DynamicDataSourceStrategy;
import cn.waynechu.bootstarter.dynamicdatasource.toolkit.DynamicDataSourceContextHolder;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源核心组件
 *
 * @author zhuwei
 * @date 2019/1/15 17:29
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {

    /**
     * 分组标识。如 order-slave1，order-slave2 会划分到 order 组中
     */
    public static final String GROUP_FLAG = "-";

    /**
     * 主数据源，该数据源用于当拦截器未生效时，选择该数据源
     * 比如初始化时 LazyConnectionDataSourceProxy 设置 defaultAutoCommit 和 defaultTransactionIsolation
     */
    private DataSource primaryDataSource;

    /**
     * 是否打印动态数据源执行情况
     */
    @Setter
    public boolean loggerEnable;
    @Setter
    protected DynamicDataSourceProvider provider;
    @Setter
    protected Class<? extends DynamicDataSourceStrategy> strategy;

    /**
     * 所有数据源
     */
    private Map<String, DataSource> allDataSource = new LinkedHashMap<>();
    /**
     * 分组数据库
     */
    private Map<String, DynamicGroupDataSource> groupDataSources = new ConcurrentHashMap<>();

    @Override
    protected DataSource determineDataSource() {
        String lookUpKey = DynamicDataSourceContextHolder.peek();
        DataSource dataSource = this.getDataSource(lookUpKey);

        if (dataSource instanceof DruidDataSource) {
            String dataSourceName = ((DruidDataSource) dataSource).getName();
            if (loggerEnable) {
                log.info("使用动态数据源 [{}]", dataSourceName);
            }
        }
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, DataSource> dataSources = provider.loadDataSources();

        log.info("读取到 [{}] 个数据源, 开始动态数据源分组...", dataSources.size());
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            // 选择读取到的第一个数据源作为主数据源
            if (primaryDataSource == null) {
                primaryDataSource = entry.getValue();
            }
            this.addDataSource(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 添加数据源
     *
     * @param dataSourceName 数据源名称
     * @param dataSource     数据源
     */
    public void addDataSource(String dataSourceName, DataSource dataSource) {
        if (dataSourceName.contains(GROUP_FLAG)) {
            addGroupDataSource(dataSourceName, dataSource);
        }
        allDataSource.put(dataSourceName, dataSource);
    }

    /**
     * 添加组数据源
     *
     * @param dataSourceName 数据源名称
     * @param dataSource     数据源
     */
    private void addGroupDataSource(String dataSourceName, DataSource dataSource) {
        String dataSourceType = "";
        String groupName = dataSourceName.split(GROUP_FLAG)[0];
        if (groupDataSources.containsKey(groupName)) {
            if (dataSourceName.contains(DynamicDataSourceContextHolder.DATASOURCE_MASTER_FLAG)) {
                groupDataSources.get(groupName).addMaster(dataSource);
                dataSourceType = DynamicDataSourceContextHolder.DATASOURCE_MASTER_FLAG;
            } else {
                groupDataSources.get(groupName).addSlave(dataSource);
                dataSourceType = DynamicDataSourceContextHolder.DATASOURCE_SALVE_FLAG;
            }
        } else {
            try {
                DynamicGroupDataSource groupDatasource = new DynamicGroupDataSource(groupName, strategy.newInstance());
                if (dataSourceName.contains(DynamicDataSourceContextHolder.DATASOURCE_MASTER_FLAG)) {
                    groupDatasource.addMaster(dataSource);
                    dataSourceType = DynamicDataSourceContextHolder.DATASOURCE_MASTER_FLAG;
                } else {
                    groupDatasource.addSlave(dataSource);
                    dataSourceType = DynamicDataSourceContextHolder.DATASOURCE_SALVE_FLAG;
                }
                groupDataSources.put(groupName, groupDatasource);
            } catch (Exception e) {
                log.error("数据源 [{}] 分组失败", dataSourceName, e);
            }
        }
        log.info("添加数据源 [{}] 至 [{}] 分组, 该数据源类型为 [{}]", dataSourceName, groupName, dataSourceType);
    }

    /**
     * 获取数据源
     *
     * @param lookUpKey 用于获取数据源的key
     * @return 数据源
     */
    private DataSource getDataSource(String lookUpKey) {
        if (StringUtils.isEmpty(lookUpKey)) {
            return primaryDataSource;
        }

        DataSource dataSource = allDataSource.get(lookUpKey);
        if (dataSource != null) {
            return dataSource;
        }

        if (lookUpKey.contains(GROUP_FLAG)) {
            String[] splitStr = lookUpKey.split(GROUP_FLAG);
            String groupName = splitStr[0];
            String dataSourceType = splitStr[1];

            DynamicGroupDataSource dynamicGroupDataSource = groupDataSources.get(groupName);
            if (dynamicGroupDataSource != null) {
                return this.getFromGroupDataSource(groupName, dataSourceType);
            }
        }
        throw new RuntimeException("Unknown lookUpKey.");
    }

    /**
     * 从组数据源中获取数据源
     *
     * @param groupName      组名
     * @param dataSourceType 数据源类型 master | slave
     * @return 数据源
     */
    private DataSource getFromGroupDataSource(String groupName, String dataSourceType) {
        DataSource dataSource;
        DynamicGroupDataSource groupDataSource = groupDataSources.get(groupName);
        if (DynamicDataSourceContextHolder.DATASOURCE_MASTER_FLAG.equals(dataSourceType)
                || groupDataSource.getSlavesSize() == 0) {
            dataSource = groupDataSource.determineMaster();
        } else if (DynamicDataSourceContextHolder.DATASOURCE_SALVE_FLAG.equals(dataSourceType)) {
            dataSource = groupDataSource.determineSlave();
        } else {
            throw new IllegalArgumentException("Unknown datasource type.");
        }
        return dataSource;
    }
}
