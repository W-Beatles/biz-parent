package cn.waynechu.datasource;

import cn.waynechu.datasource.dynamic.DynamicDataSourceInterceptor;
import cn.waynechu.datasource.dynamic.DynamicRoutingDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableConfigurationProperties({DruidDataSourceProperties.class})
@ConditionalOnProperty(name = "druid.dynamic.datasource.master-url", matchIfMissing = false)
public class DruidDataSourceAutoConfiguration {
    private static final String MAPPER_LOCATION = "classpath*:sqlmap/*Mapper.xml";

    @Autowired
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "master")
    public DataSource master() {
        return new DruidDataSourceBuilder()
                .setUrl(druidDataSourceProperties.getMasterUrl())
                .setUsername(druidDataSourceProperties.getUsername())
                .setPassword(druidDataSourceProperties.getPassword())
                .build();
    }

    @Bean("slaves")
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
    public DataSource dataSource(@Qualifier("dynamicRoutingDataSource") DynamicRoutingDataSource dynamicRoutingDataSource) {
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy();
        dataSource.setTargetDataSource(dynamicRoutingDataSource);
        return dataSource;
    }

    @Bean(name = "defaultTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "defaultTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("defaultTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("defaultDataSource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPlugins(new Interceptor[]{new DynamicDataSourceInterceptor()});
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        sessionFactoryBean.setTypeAliasesPackage("cn.waynechu.app.dal.entity");

        SqlSessionFactory sqlSessionFactory = sessionFactoryBean.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactory;
    }
}
