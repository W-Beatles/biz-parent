package cn.waynechu.datasource;

import cn.waynechu.datasource.dynamic.DynamicRoutingDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:28
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DruidDataSourceProperties.class})
@ConditionalOnProperty(name = "spring.datasource.druid.url", matchIfMissing = false)
public class DruidDataSourceAutoConfiguration {

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "master")
    public DataSource master() {
        return new DruidDataSourceBuilder()
                .setUrl(druidDataSourceProperties.getUrl())
                .setUsername(druidDataSourceProperties.getUsername())
                .setPassword(druidDataSourceProperties.getPassword())
                .build();
    }

    @Bean("slaves")
    @ConditionalOnProperty(name = "spring.datasource.druid.slave-urls")
    public List<DataSource> slaves() {
        List<DataSource> slaveDataSources = new ArrayList<>();

        List<String> slaveUrls = druidDataSourceProperties.getSlaveUrls();

        if (!CollectionUtils.isEmpty(slaveUrls)) {
            DruidDataSource slaveDataSource;
            for (String slaveUrl : slaveUrls) {
                slaveDataSource = new DruidDataSourceBuilder()
                        .setUrl(slaveUrl)
                        .setUsername(druidDataSourceProperties.getUsername())
                        .setPassword(druidDataSourceProperties.getPassword())
                        .build();
                slaveDataSources.add(slaveDataSource);
            }
        }
        return slaveDataSources;
    }

    @Bean("dynamicRoutingDataSource")
    public DynamicRoutingDataSource dynamicDataSource(@Qualifier("master") DataSource master,
                                                      @Qualifier("slaves") List<DataSource> slaves) {
        DynamicRoutingDataSource dynamic = new DynamicRoutingDataSource();
        dynamic.setReadDataSourceSelectPattern(0);
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
}
