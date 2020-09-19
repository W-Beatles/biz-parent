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
package cn.waynechu.bootstarter.dynamicdatasource.strategy;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态数据源选择策略 - 轮询策略
 *
 * @author zhuwei
 * @since 2019/1/15 16:48
 */
@Slf4j
public class RoundRobinDynamicDataSourceStrategy extends AbstractDynamicDataSourceStrategy {

    /**
     * 原子计数器
     */
    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public DataSource determineSlave(List<DataSource> dataSources) {
        return dataSources.get(Math.abs(index.getAndAdd(1) % (dataSources.size() - 1)) + 1);
    }
}
