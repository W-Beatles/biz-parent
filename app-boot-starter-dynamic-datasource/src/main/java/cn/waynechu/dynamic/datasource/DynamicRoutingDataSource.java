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

import cn.waynechu.dynamic.datasource.provider.DynamicDataSourceProvider;
import cn.waynechu.dynamic.datasource.strategy.DynamicDataSourceStrategy;
import cn.waynechu.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    /**
     * 分组前缀。如slave_1，slave_2会划分为一组
     */
    private static final String UNDERLINE = "_";
    @Setter
    protected DynamicDataSourceProvider provider;
    @Setter
    protected Class<? extends DynamicDataSourceStrategy> strategy;
    @Setter
    protected String primary;

    /**
     * 所有数据源
     */
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    /**
     * 分组数据库
     */
    private Map<String, DynamicGroupDataSource> groupDataSources = new ConcurrentHashMap<>();

    @Override
    protected DataSource determineDataSource() {
        return getDataSource(DynamicDataSourceContextHolder.peek());
    }

    public void init() {
        Map<String, DataSource> dataSources = provider.loadDataSources();
        log.info("读取到 [{}] 个数据源，开始动态数据源分组...", dataSources.size());
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            addDataSource(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取数据源
     *
     * @param groupName 数据源组名
     * @return 数据源
     */
    public DataSource getDataSource(String groupName) {
        DataSource dataSource;
        if (StringUtils.isEmpty(groupName)) {
            dataSource = determinePrimaryDataSource();
        } else if (!groupDataSources.isEmpty() && groupDataSources.containsKey(groupName)) {
            dataSource = groupDataSources.get(groupName).determineDataSource();
        } else if (dataSourceMap.containsKey(groupName)) {
            dataSource = dataSourceMap.get(groupName);
        } else {
            dataSource = determinePrimaryDataSource();
        }
        if (dataSource instanceof DruidDataSource) {
            String dataSourceName = ((DruidDataSource) dataSource).getName();
            log.debug("使用动态数据源 [{}]", dataSourceName);
        }
        return dataSource;
    }

    private DataSource determinePrimaryDataSource() {
        return dataSourceMap.containsKey(primary) ? dataSourceMap.get(primary) : groupDataSources.get(primary).determineDataSource();
    }

    private void addDataSource(String name, DataSource dataSource) {
        dataSourceMap.put(name, dataSource);
        if (name.contains(UNDERLINE)) {
            String group = name.split(UNDERLINE)[0];
            if (groupDataSources.containsKey(group)) {
                groupDataSources.get(group).addDatasource(dataSource);
            } else {
                try {
                    DynamicGroupDataSource groupDatasource = new DynamicGroupDataSource(group, strategy.newInstance());
                    groupDatasource.addDatasource(dataSource);
                    groupDataSources.put(group, groupDatasource);
                } catch (Exception e) {
                    log.error("数据源分组 [{}] 失败", name, e);
                    dataSourceMap.remove(name);
                }
            }
        }
        log.info("数据源 [{}] 分组成功", name);
    }
}
