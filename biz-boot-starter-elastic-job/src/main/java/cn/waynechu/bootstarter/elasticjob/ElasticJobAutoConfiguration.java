package cn.waynechu.bootstarter.elasticjob;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/2/25 16:52
 */
@Configuration
@ConditionalOnProperty(value = "elastic.job.enable", havingValue = "true")
@EnableConfigurationProperties(ElasticJobProperties.class)
public class ElasticJobAutoConfiguration {

    private static ApplicationContext applicationContext;

    @Autowired
    private ElasticJobProperties elasticJobProperties;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        ElasticJobAutoConfiguration.applicationContext = applicationContext;
    }

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(ZookeeperRegistryCenter.class)
    public ZookeeperRegistryCenter regCenter() {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(elasticJobProperties.getServerLists(),
                elasticJobProperties.getNamespace()));
    }

    @Bean
    @ConditionalOnMissingBean(JobEventConfiguration.class)
    @ConditionalOnExpression("${elastic.job.log:true}")
    public JobEventConfiguration jobEventConfiguration() throws Exception {
        final DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(elasticJobProperties.getLogds().getUrl());
        dataSource.setUsername(elasticJobProperties.getLogds().getUsername());
        dataSource.setPassword(elasticJobProperties.getLogds().getPassword());
        dataSource.setConnectionProperties(
                "config.decrypt=true;config.decrypt.key=" + elasticJobProperties.getLogds().getPwdPublicKey());
        dataSource.setFilters("config");
        dataSource.setMaxActive(5);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(false);
        Runtime.getRuntime().addShutdownHook(new Thread(dataSource::close));
        dataSource.init();
        return new JobEventRdbConfiguration(dataSource);
    }

    public static <T> T getBean(Class<T> clazz) {
        @SuppressWarnings("rawtypes")
        Map beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return (T) beanMaps.values().iterator().next();
        } else {
            return null;
        }
    }
}
