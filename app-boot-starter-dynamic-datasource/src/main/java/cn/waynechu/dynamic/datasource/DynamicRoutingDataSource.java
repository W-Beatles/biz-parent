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

    /**
     * 获取数据源
     *
     * @param ds 数据源名称
     * @return 数据源
     */
    public DataSource getDataSource(String ds) {
        if (StringUtils.isEmpty(ds)) {
            return determinePrimaryDataSource();
        } else if (!groupDataSources.isEmpty() && groupDataSources.containsKey(ds)) {
            log.debug("从 [{}] 组数据源中返回数据源", ds);
            return groupDataSources.get(ds).determineDataSource();
        } else if (dataSourceMap.containsKey(ds)) {
            log.debug("从 [{}] 单数据源中返回数据源", ds);
            return dataSourceMap.get(ds);
        }
        return determinePrimaryDataSource();
    }

    private DataSource determinePrimaryDataSource() {
        log.debug("从默认数据源中返回数据");
        return dataSourceMap.containsKey(primary) ? dataSourceMap.get(primary) : groupDataSources.get(primary).determineDataSource();
    }

    public void init() {
        Map<String, DataSource> dataSources = provider.loadDataSources();
        log.info("读取到 {} 个数据源，开始加载...", dataSources.size());
        // 添加并分组数据源
        for (Map.Entry<String, DataSource> entry : dataSources.entrySet()) {
            addDataSource(entry.getKey(), entry.getValue());
        }
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
                    log.error("加载数据源失败", e);
                    dataSourceMap.remove(name);
                }
            }
        }
        log.info("加载数据源 {} 成功", name);
    }
}
