package cn.waynechu.datasource;

import cn.waynechu.datasource.dynamic.DynamicDataSource;
import cn.waynechu.datasource.dynamic.DynamicDataSourceInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
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
@EnableConfigurationProperties({DruidDataSourcePropertity.class})
@ConditionalOnProperty(name = "druid.datasource.master-url", matchIfMissing = false)
public class DruidDataSourceAutoConfiguration {
    private static final String MAPPER_LOCATION = "classpath*:sqlmap/*Mapper.xml";

    @Autowired
    private DruidDataSourcePropertity druidDataSourcePropertity;

    @Bean(name = "master")
    public DataSource master() {
        return new DruidDataSourceBuilder()
                .setUrl(druidDataSourcePropertity.getMasterUrl())
                .setUsername(druidDataSourcePropertity.getUsername())
                .setPassword(druidDataSourcePropertity.getPassword())
                .build();
    }

    @Bean("slaves")
    public List<DataSource> slaves() {
        List<DataSource> slaveDataSources = new ArrayList<>();

        List<String> slaveUrls = druidDataSourcePropertity.getSlaveUrls();

        if (!CollectionUtils.isEmpty(slaveUrls)) {
            DruidDataSource slaveDataSource;
            for (String slaveUrl : slaveUrls) {
                slaveDataSource = new DruidDataSourceBuilder()
                        .setUrl(slaveUrl)
                        .setUsername(druidDataSourcePropertity.getUsername())
                        .setPassword(druidDataSourcePropertity.getPassword())
                        .build();
                slaveDataSources.add(slaveDataSource);
            }
        }
        return slaveDataSources;
    }

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("master") DataSource master,
                                               @Qualifier("slaves") List<DataSource> slaves) {
        DynamicDataSource dynamic = new DynamicDataSource();
        dynamic.setReadDataSourceSelectPattern(0);
        dynamic.setMaster(master);
        dynamic.setSlaves(slaves);
        return dynamic;
    }

    @Bean("defaultDruidDataSource")
    @ConditionalOnMissingBean(name = "defaultDruidDataSource")
    @Primary
    public DataSource dataSource(DynamicDataSource dynamicDataSource) {
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy();
        dataSource.setTargetDataSource(dynamicDataSource);
        return dataSource;
    }

    @Bean(name = "defaultTransactionManager")
    @ConditionalOnMissingBean(name = "defaultTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("defaultDruidDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "defaultTransactionTemplate")
    @ConditionalOnMissingBean(name = "defaultTransactionTemplate")
    @Primary
    public TransactionTemplate transactionTemplate(@Qualifier("defaultTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "defaultSqlSessionFactory")
    @ConditionalOnMissingBean(name = "defaultSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("defaultDruidDataSource") DataSource defaultDruidDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDruidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.addInterceptor(new DynamicDataSourceInterceptor());
    }
}
