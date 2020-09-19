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
package cn.waynechu.bootstarter.dynamicdatasource.dynamic;

import cn.waynechu.bootstarter.dynamicdatasource.strategy.DynamicDataSourceStrategy;
import lombok.Data;

import javax.sql.DataSource;
import java.util.LinkedList;

/**
 * 分组数据源
 * 取组内第一个数据源为主数据源
 *
 * @author zhuwei
 * @since 2019/1/15 19:20
 */
@Data
public class DynamicGroupDataSource {

    /**
     * 组名
     */
    private String groupName;

    /**
     * 数据源切换策略
     */
    private DynamicDataSourceStrategy dynamicDataSourceStrategy;

    /**
     * 当前组下所有数据源
     */
    private LinkedList<DataSource> dataSources = new LinkedList<>();

    public DynamicGroupDataSource(String groupName, DynamicDataSourceStrategy dynamicDataSourceStrategy) {
        this.groupName = groupName;
        this.dynamicDataSourceStrategy = dynamicDataSourceStrategy;
    }

    public void addMaster(DataSource dataSource) {
        dataSources.addFirst(dataSource);
    }

    public void addSlave(DataSource dataSource) {
        dataSources.add(dataSource);
    }

    public void removeDatasource(DataSource dataSource) {
        dataSources.remove(dataSource);
    }

    public DataSource determineMaster() {
        return dynamicDataSourceStrategy.determineMaster(dataSources);
    }

    public DataSource determineSlave() {
        return dynamicDataSourceStrategy.determineSlave(dataSources);
    }

    public int size() {
        return dataSources.size();
    }

    public int getSlavesSize() {
        return dataSources.isEmpty() ? 0 : dataSources.size() - 1;
    }
}
