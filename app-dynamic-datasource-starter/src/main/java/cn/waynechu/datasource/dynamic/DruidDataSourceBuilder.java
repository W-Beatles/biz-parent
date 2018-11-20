package cn.waynechu.datasource.dynamic;

import cn.waynechu.datasource.properties.DruidDataSourceProperties;
import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:02
 */
public class DruidDataSourceBuilder {
    private static final Logger log = LoggerFactory.getLogger(DruidDataSourceBuilder.class);
    private DruidDataSourceProperties properties;

    public DruidDataSourceBuilder(DruidDataSourceProperties properties) {
        this.properties = properties;
    }

    public DataSource buildMaster() {
        log.debug("Prepared master dataSource，url: {}", properties.getUrl());
        return build(properties.getUrl());
    }

    public List<DataSource> buildSlaves() {
        List<DataSource> slavesDataSource = new ArrayList<>();

        DruidDataSource slaveDataSource;
        for (String slaveUrl : properties.getSlaveUrls()) {
            if (!StringUtils.isEmpty(slaveUrl)) {
                slaveDataSource = build(slaveUrl);
                slavesDataSource.add(slaveDataSource);
                log.debug("Prepared slave dataSource，url: {}", slaveUrl);
            }
        }
        return slavesDataSource;
    }

    private DruidDataSource build(String url) {
        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setUrl(url);
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setConnectionProperties("config.decrypt=true;config.decrypt.key=" + properties.getPublicKey());
        try {
            druidDataSource.setFilters("stat,wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setValidationQueryTimeout(properties.getValidationQueryTimeout());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());

        return druidDataSource;
    }
}