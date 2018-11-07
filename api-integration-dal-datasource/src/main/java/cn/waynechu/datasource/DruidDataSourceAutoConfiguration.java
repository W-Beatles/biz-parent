package cn.waynechu.datasource;

import cn.waynechu.datasource.dynamic.DynamicDataSource;
import cn.waynechu.datasource.dynamic.DynamicDataSourceInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:28
 */
@Configuration
@EnableConfigurationProperties({DruidDataSourceConfigBean.class})
@ConditionalOnProperty(name = "druid.datasource.master-url")
//@MapperScan(value = {"cn.waynechu.dal.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DruidDataSourceAutoConfiguration {
    private static final String MAPPER_LOCATION = "classpath*:sqlmap/*Mapper.xml";

    @Autowired
    private DruidDataSourceConfigBean druidDataSourceConfigBean;

    @Bean(name = "master", initMethod = "init", destroyMethod = "close")
    public DruidDataSource master() {
        return new DruidDataSourceBuilder()
                .setUrl(druidDataSourceConfigBean.getMasterUrl())
                .setUsername(druidDataSourceConfigBean.getUsername())
                .setPassword(druidDataSourceConfigBean.getPassword())
                .build();
    }

    @Bean("slaves")
    public List<DruidDataSource> slaves() {
        List<DruidDataSource> slaveDataSources = new ArrayList<>();

        List<String> slaveUrls = druidDataSourceConfigBean.getSlaveUrls();

        if (!CollectionUtils.isEmpty(slaveUrls)) {
            DruidDataSource slaveDataSource;
            for (String slaveUrl : slaveUrls) {
                slaveDataSource = new DruidDataSourceBuilder()
                        .setUrl(slaveUrl)
                        .setUsername(druidDataSourceConfigBean.getUsername())
                        .setPassword(druidDataSourceConfigBean.getPassword())
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

    @Bean("dataSource")
    public LazyConnectionDataSourceProxy dataSource(DynamicDataSource dynamicDataSource) {
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy();
        dataSource.setTargetDataSource(dynamicDataSource);
        return dataSource;
    }

    @Bean("transactionManager")
    @ConditionalOnMissingBean(name = {"transactionManager"})
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean session = new SqlSessionFactoryBean();
        session.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        session.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
        return session;
    }

    @Bean
    @DependsOn("sqlSessionFactory")
    public MapperScannerConfigurer mapperScanner() {
        MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
        mapperScanner.setBasePackage("cn.waynechu.dal.mapper");
        mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScanner;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.addInterceptor(new DynamicDataSourceInterceptor());
    }
}
