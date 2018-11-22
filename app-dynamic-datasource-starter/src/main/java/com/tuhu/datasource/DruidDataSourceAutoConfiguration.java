package cn.waynechu.datasource;

import com.tuhu.datasource.dynamic.DruidDataSourceBuilder;
import com.tuhu.datasource.dynamic.DynamicRoutingDataSource;
import com.tuhu.datasource.interceptor.DynamicDataSourceInterceptor;
import cn.waynechu.datasource.properties.DruidDataSourceProperties;
import cn.waynechu.datasource.properties.DruidStatProperties;
import cn.waynechu.datasource.stat.DruidFilterConfiguration;
import cn.waynechu.datasource.stat.DruidSpringAopConfiguration;
import cn.waynechu.datasource.stat.DruidStatViewServletConfiguration;
import cn.waynechu.datasource.stat.DruidWebStatFilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:28
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DruidDataSourceProperties.class, DruidStatProperties.class})
@Import({DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
@ConditionalOnProperty(name = "spring.datasource.druid.url", matchIfMissing = false)
public class DruidDataSourceAutoConfiguration {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "master")
    public DataSource master() {
        return new DruidDataSourceBuilder(druidDataSourceProperties).buildMaster();
    }

    @Bean("slaves")
    @ConditionalOnProperty(name = "spring.datasource.druid.slave-urls")
    public List<DataSource> slaves() {
        return new DruidDataSourceBuilder(druidDataSourceProperties).buildSlaves();
    }

    @Bean("dynamicRoutingDataSource")
    public DynamicRoutingDataSource dynamicDataSource(@Qualifier("master") DataSource master,
                                                      @Qualifier("slaves") List<DataSource> slaves) {
        DynamicRoutingDataSource dynamic = new DynamicRoutingDataSource();
        dynamic.setRoutingPattern(druidDataSourceProperties.getRoutingPattern());
        dynamic.setMaster(master);
        dynamic.setSlaves(slaves);
        return dynamic;
    }

    @Bean("defaultDataSource")
    @Primary
    public DataSource dataSource(@Qualifier("dynamicRoutingDataSource") DynamicRoutingDataSource dynamicRoutingDataSource) {
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy();
        dataSource.setTargetDataSource(dynamicRoutingDataSource);
        return dataSource;
    }

    @Bean("dynamicDataSourceInterceptor")
    @ConditionalOnProperty("spring.datasource.druid.slave-urls")
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}
