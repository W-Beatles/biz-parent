package cn.waynechu.dynamic.datasource;

import cn.waynechu.dynamic.datasource.dynamic.DruidDataSourceBuilder;
import cn.waynechu.dynamic.datasource.dynamic.DynamicRoutingDataSource;
import cn.waynechu.dynamic.datasource.interceptor.DynamicDataSourceInterceptor;
import cn.waynechu.dynamic.datasource.properties.DruidDataSourceProperties;
import cn.waynechu.dynamic.datasource.properties.DruidStatProperties;
import cn.waynechu.dynamic.datasource.stat.DruidFilterConfiguration;
import cn.waynechu.dynamic.datasource.stat.DruidSpringAopConfiguration;
import cn.waynechu.dynamic.datasource.stat.DruidStatViewServletConfiguration;
import cn.waynechu.dynamic.datasource.stat.DruidWebStatFilterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:28
 */
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DruidDataSourceProperties.class, DruidStatProperties.class})
@Import({DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
@ConditionalOnProperty(name = "spring.datasource.druid.url")
public class DruidDataSourceAutoConfiguration {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "master")
    public DataSource master() {
        return new DruidDataSourceBuilder(druidDataSourceProperties).buildMaster();
    }

    @Bean("slaves")
    @ConditionalOnProperty(name = "spring.datasource.druid.slave-urls[0]", matchIfMissing = true)
    public List<DataSource> slaves() {
        return new DruidDataSourceBuilder(druidDataSourceProperties).buildSlaves();
    }

    @Bean("dynamicRoutingDataSource")
    @DependsOn({"master", "slaves"})
    public DynamicRoutingDataSource dynamicDataSource(@Qualifier("master") DataSource master,
                                                      @Qualifier("slaves") List<DataSource> slaves) {
        DynamicRoutingDataSource dynamic = new DynamicRoutingDataSource();
        dynamic.setRoutingPattern(druidDataSourceProperties.getRoutingPattern());
        dynamic.setMaster(master);
        dynamic.setSlaves(slaves);
        return dynamic;
    }

    @Bean("defaultDataSource")
    @DependsOn({"dynamicRoutingDataSource"})
    @Primary
    public DataSource dataSource(@Qualifier("dynamicRoutingDataSource") DynamicRoutingDataSource dynamicRoutingDataSource) {
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy();
        dataSource.setTargetDataSource(dynamicRoutingDataSource);
        return dataSource;
    }

    @Bean("dynamicDataSourceInterceptor")
    @ConditionalOnProperty("spring.datasource.druid.slave-urls[0]")
    public DynamicDataSourceInterceptor dynamicDataSourceInterceptor() {
        return new DynamicDataSourceInterceptor();
    }
}
