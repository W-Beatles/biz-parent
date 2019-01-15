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
package cn.waynechu.dynamic.datasource.autoconfig.druid;

import cn.waynechu.dynamic.datasource.autoconfig.druid.stat.DruidFilterConfiguration;
import cn.waynechu.dynamic.datasource.autoconfig.druid.stat.DruidSpringAopConfiguration;
import cn.waynechu.dynamic.datasource.autoconfig.druid.stat.DruidStatViewServletConfiguration;
import cn.waynechu.dynamic.datasource.autoconfig.druid.stat.DruidWebStatFilterConfiguration;
import cn.waynechu.dynamic.datasource.properties.DruidStatProperties;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhuwei
 * @date 2019/1/15 17:11
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@EnableConfigurationProperties({DruidStatProperties.class})
@Import({
        DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
public class DruidDynamicDataSourceConfiguration {
}
