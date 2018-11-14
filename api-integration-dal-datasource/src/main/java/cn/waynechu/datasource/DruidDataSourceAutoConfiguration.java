package cn.waynechu.datasource;

import cn.waynechu.datasource.dynamic.DynamicDataSource;
import cn.waynechu.datasource.dynamic.DynamicDataSourceInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@ConditionalOnClass(DruidDataSourcePropertity.class)
@AutoConfigureBefore(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({DruidDataSourcePropertity.class})
public class DruidDataSourceAutoConfiguration {
    private static final String MAPPER_LOCATION = "classpath*:sqlmap/*Mapper.xml";

    @Autowired
    private DruidDataSourcePropertity druidDataSourcePropertity;

    @Bean(name = "master", initMethod = "init", destroyMethod = "close")
    public DruidDataSource master() {
        return new DruidDataSourceBuilder()
                .setUrl(druidDataSourcePropertity.getMasterUrl())
                .setUsername(druidDataSourcePropertity.getUsername())
                .setPassword(druidDataSourcePropertity.getPassword())
                .build();
    }

    @Bean("slaves")
    public List<DruidDataSource> slaves() {
        List<DruidDataSource> slaveDataSources = new ArrayList<>();

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
