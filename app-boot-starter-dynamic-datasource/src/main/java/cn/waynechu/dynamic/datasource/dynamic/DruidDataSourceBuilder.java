package cn.waynechu.dynamic.datasource.dynamic;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import cn.waynechu.dynamic.datasource.properties.DruidDataSourceProperties;
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
    private DruidDataSourceProperties druidDataSourceProperties;

    public DruidDataSourceBuilder(DruidDataSourceProperties properties) {
        this.druidDataSourceProperties = properties;
    }

    public DataSource buildMaster() {
        log.debug("Prepared master dataSource，url: {}", druidDataSourceProperties.getUrl());
        return build(druidDataSourceProperties.getUrl());
    }

    public List<DataSource> buildSlaves() {
        List<DataSource> slavesDataSource = new ArrayList<>();

        DruidDataSource slaveDataSource;
        for (String slaveUrl : druidDataSourceProperties.getSlaveUrls()) {
            if (!StringUtils.isEmpty(slaveUrl)) {
                slaveDataSource = build(slaveUrl);
                slavesDataSource.add(slaveDataSource);
                log.debug("Prepared slave dataSource，url: {}", slaveUrl);
            }
        }
        return slavesDataSource;
    }

    private DruidDataSource build(String url) {
        DruidDataSource result = new DruidDataSource();
        result.setUrl(url);
        result.setUsername(druidDataSourceProperties.getUsername());
        result.setPassword(druidDataSourceProperties.getPassword());
        if (!StringUtils.isEmpty(druidDataSourceProperties.getPwdPublicKey())) {
            result.setConnectionProperties(
                    "config.decrypt=true;config.decrypt.key=" + druidDataSourceProperties.getPwdPublicKey());
            try {
                result.setFilters("config");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        result.setMaxActive(druidDataSourceProperties.getMaxActive());
        result.setInitialSize(druidDataSourceProperties.getInitialSize());
        result.setMaxWait(druidDataSourceProperties.getMaxWait());
        result.setMinIdle(druidDataSourceProperties.getMinIdle());
        result.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        result.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        result.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        result.setValidationQueryTimeout(druidDataSourceProperties.getValidationQueryTimeout());
        result.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        result.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        result.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        result.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        result.setMaxOpenPreparedStatements(druidDataSourceProperties.getMaxOpenPreparedStatements());

        if (druidDataSourceProperties.isEnableMonitor()) {
            StatFilter filter = new StatFilter();
            filter.setLogSlowSql(druidDataSourceProperties.isLogSlowSql());
            filter.setMergeSql(druidDataSourceProperties.isMergeSql());
            filter.setSlowSqlMillis(druidDataSourceProperties.getSlowSqlMillis());

            List<Filter> list = new ArrayList<>();
            list.add(filter);
            result.setProxyFilters(list);
        }
        return result;
    }
}