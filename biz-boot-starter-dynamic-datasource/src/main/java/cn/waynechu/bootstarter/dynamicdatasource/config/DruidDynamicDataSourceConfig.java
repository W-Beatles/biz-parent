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
package cn.waynechu.bootstarter.dynamicdatasource.config;

import cn.waynechu.bootstarter.dynamicdatasource.config.stat.DruidFilterConfiguration;
import cn.waynechu.bootstarter.dynamicdatasource.config.stat.DruidSpringAopConfiguration;
import cn.waynechu.bootstarter.dynamicdatasource.config.stat.DruidStatViewServletConfiguration;
import cn.waynechu.bootstarter.dynamicdatasource.config.stat.DruidWebStatFilterConfiguration;
import cn.waynechu.bootstarter.dynamicdatasource.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhuwei
 * @since 2019/1/15 17:11
 */
@Configuration
@ConditionalOnClass(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({DruidStatProperties.class})
@Import({
        DruidStatViewServletConfiguration.class,
        DruidSpringAopConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
public class DruidDynamicDataSourceConfig {
}
