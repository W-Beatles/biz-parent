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
package cn.waynechu.dynamic.datasource.strategy;

import javax.sql.DataSource;
import java.util.LinkedList;

/**
 * 动态数据源选择策略
 * <p>
 * 默认为负载均衡策略，使用轮询算法。
 * 你可以实现该接口自定义动态数据源切换策略
 *
 * @author zhuwei
 * @date 2019/1/15 16:47
 */
public interface DynamicDataSourceStrategy {

    /**
     * 决定当前数据源
     *
     * @param dataSources 数据源选择库
     * @return dataSource 所选择的数据源
     */
    DataSource determineDataSource(LinkedList<DataSource> dataSources);
}
