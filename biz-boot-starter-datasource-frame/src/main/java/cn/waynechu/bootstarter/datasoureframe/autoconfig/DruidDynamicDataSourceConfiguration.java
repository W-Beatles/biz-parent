package cn.waynechu.bootstarter.datasoureframe.autoconfig;

import cn.waynechu.bootstarter.datasoureframe.autoconfig.stat.DruidSpringAopConfiguration;
import cn.waynechu.bootstarter.datasoureframe.autoconfig.stat.DruidStatViewServletConfiguration;
import cn.waynechu.bootstarter.datasoureframe.autoconfig.stat.DruidWebStatFilterConfiguration;
import cn.waynechu.bootstarter.datasoureframe.properties.DruidStatProperties;
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
        DruidStatViewServletConfiguration.class,
        DruidSpringAopConfiguration.class,
        DruidWebStatFilterConfiguration.class})
public class DruidDynamicDataSourceConfiguration {

}
