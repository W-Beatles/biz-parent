package cn.waynechu.datasource;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/7 14:02
 */
public class DruidDataSourceBuilder {
    private DruidDataSourceProperties properties;

    public DruidDataSourceBuilder(DruidDataSourceProperties properties) {
        this.properties = properties;
    }

    public DataSource buildMaster() {
        return build(properties.getUrl());
    }

    public List<DataSource> buildSlaves() {
        List<DataSource> slavesDataSource = new ArrayList<>();

        DruidDataSource slaveDataSource;
        for (String slaveUrl : properties.getSlaveUrls()) {
            slaveDataSource = build(slaveUrl);
            slavesDataSource.add(slaveDataSource);
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