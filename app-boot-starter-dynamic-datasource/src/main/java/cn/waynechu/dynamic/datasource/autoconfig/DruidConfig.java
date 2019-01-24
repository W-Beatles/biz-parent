/**
 * Copyright © 2018 organization waynechu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.waynechu.dynamic.datasource.autoconfig;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Properties;

import static com.alibaba.druid.pool.DruidAbstractDataSource.*;

/**
 * Druid数据源配置
 *
 * @author zhuwei
 * @date 2019/1/15 15:50
 */
@Slf4j
@Data
public class DruidConfig {
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Long timeBetweenLogStatsMillis;
    private Integer statSqlMaxSize;
    private Long minEvictableIdleTimeMillis;
    private Long maxEvictableIdleTimeMillis;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private String validationQuery;
    private Integer validationQueryTimeout;
    private Boolean useGlobalDataSourceStat;
    private Boolean asyncInit;
    private String filters;
    private Boolean clearFiltersEnable;
    private Boolean resetStatEnable;
    private Integer notFullTimeoutRetryCount;
    private Integer maxWaitThreadCount;
    private Boolean failFast;
    private Long phyTimeoutMillis;
    private Boolean keepAlive;
    private Boolean poolPreparedStatements;
    private Boolean initVariants;
    private Boolean initGlobalVariants;
    private Boolean useUnfairLock;
    private Boolean killWhenSocketReadTimeout;
    private Properties connectionProperties;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String initConnectionSqls;
    private Boolean sharePreparedStatements;
    private Integer connectionErrorRetryAttempts;
    private Boolean breakAfterAcquireFailure;

    private String publicKey;

    public Properties toProperties(DruidConfig globalConfig) {
        Properties properties = new Properties();
        //
        Integer tempInitialSize = initialSize == null ? globalConfig.getInitialSize() : initialSize;
        if (tempInitialSize != null && !tempInitialSize.equals(DEFAULT_INITIAL_SIZE)) {
            properties.setProperty("druid.initialSize", String.valueOf(tempInitialSize));
        }

        //
        Integer tempMaxActive = maxActive == null ? globalConfig.getMaxActive() : maxActive;
        if (tempMaxActive != null && !tempMaxActive.equals(DEFAULT_MAX_WAIT)) {
            properties.setProperty("druid.maxActive", String.valueOf(tempMaxActive));
        }

        //
        Integer tempMinIdle = minIdle == null ? globalConfig.getMinIdle() : minIdle;
        if (tempMinIdle != null && !tempMinIdle.equals(DEFAULT_MIN_IDLE)) {
            properties.setProperty("druid.minIdle", String.valueOf(tempMinIdle));
        }

        //
        Integer tempMaxWait = maxWait == null ? globalConfig.getMaxWait() : maxWait;
        if (tempMaxWait != null && !tempMaxWait.equals(DEFAULT_MAX_WAIT)) {
            properties.setProperty("druid.maxWait", String.valueOf(tempMaxWait));
        }

        //
        Long tempTimeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis == null ? globalConfig.getTimeBetweenEvictionRunsMillis() : timeBetweenEvictionRunsMillis;
        if (tempTimeBetweenEvictionRunsMillis != null && !tempTimeBetweenEvictionRunsMillis.equals(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS)) {
            properties.setProperty("druid.timeBetweenEvictionRunsMillis", String.valueOf(tempTimeBetweenEvictionRunsMillis));
        }

        //
        Long tempTimeBetweenLogStatsMillis = timeBetweenLogStatsMillis == null ? globalConfig.getTimeBetweenLogStatsMillis() : timeBetweenLogStatsMillis;
        if (tempTimeBetweenLogStatsMillis != null && tempTimeBetweenLogStatsMillis > 0) {
            properties.setProperty("druid.timeBetweenLogStatsMillis", String.valueOf(tempTimeBetweenLogStatsMillis));
        }

        //
        Integer tempStatSqlMaxSize = statSqlMaxSize == null ? globalConfig.getStatSqlMaxSize() : statSqlMaxSize;
        if (tempStatSqlMaxSize != null) {
            properties.setProperty("druid.stat.sql.MaxSize", String.valueOf(tempStatSqlMaxSize));
        }

        //
        Long tempMinEvictableIdleTimeMillis = minEvictableIdleTimeMillis == null ? globalConfig.getMinEvictableIdleTimeMillis() : minEvictableIdleTimeMillis;
        if (tempMinEvictableIdleTimeMillis != null && !tempMinEvictableIdleTimeMillis.equals(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS)) {
            properties.setProperty("druid.minEvictableIdleTimeMillis", String.valueOf(tempMinEvictableIdleTimeMillis));
        }

        //
        Long tempMaxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis == null ? globalConfig.getMaxEvictableIdleTimeMillis() : maxEvictableIdleTimeMillis;
        if (tempMaxEvictableIdleTimeMillis != null && !tempMaxEvictableIdleTimeMillis.equals(DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS)) {
            properties.setProperty("druid.maxEvictableIdleTimeMillis", String.valueOf(tempMaxEvictableIdleTimeMillis));
        }

        //
        Boolean tempTestWhileIdle = testWhileIdle == null ? globalConfig.getTestWhileIdle() : testWhileIdle;
        if (tempTestWhileIdle != null && !tempTestWhileIdle.equals(DEFAULT_WHILE_IDLE)) {
            properties.setProperty("druid.testWhileIdle", "false");
        }

        //
        Boolean tempTestOnBorrow = testOnBorrow == null ? globalConfig.getTestOnBorrow() : testOnBorrow;
        if (tempTestOnBorrow != null && !tempTestOnBorrow.equals(DEFAULT_TEST_ON_BORROW)) {
            properties.setProperty("druid.testOnBorrow", "true");
        }

        //
        String tempValidationQuery = validationQuery == null ? globalConfig.getValidationQuery() : validationQuery;
        if (tempValidationQuery != null && tempValidationQuery.length() > 0) {
            properties.setProperty("druid.validationQuery", tempValidationQuery);
        }

        //
        Boolean tempUseGlobalDataSourceStat = useGlobalDataSourceStat == null ? globalConfig.getUseGlobalDataSourceStat() : useGlobalDataSourceStat;
        if (tempUseGlobalDataSourceStat != null && tempUseGlobalDataSourceStat.equals(Boolean.TRUE)) {
            properties.setProperty("druid.useGlobalDataSourceStat", "true");
        }

        // compatible for early versions
        Boolean tempAsyncInit = asyncInit == null ? globalConfig.getAsyncInit() : asyncInit;
        if (tempAsyncInit != null && tempAsyncInit.equals(Boolean.TRUE)) {
            properties.setProperty("druid.asyncInit", "true");
        }

        // filters
        String tempFilters = filters == null ? globalConfig.getFilters() : filters;
        String tempPublicKey = publicKey == null ? globalConfig.getPublicKey() : publicKey;
        if (tempFilters == null) {
            tempFilters = tempPublicKey == null ? "" : "config";
        }
        if (tempPublicKey != null && !tempFilters.contains("config") && StringUtils.hasText(tempFilters)) {
            tempFilters += ",config";
        }
        properties.setProperty("druid.filters", tempFilters);

        //
        Properties tempConnectProperties = connectionProperties == null ? globalConfig.getConnectionProperties() : connectionProperties;
        if (tempPublicKey != null && tempPublicKey.length() > 0) {
            if (tempConnectProperties == null) {
                tempConnectProperties = new Properties();
            }
            log.info("Druid 数据源密码已加密");
            tempConnectProperties.setProperty("config.decrypt", "true");
            tempConnectProperties.setProperty("config.decrypt.key", tempPublicKey);
        }
        connectionProperties = tempConnectProperties;

        //
        Boolean tempClearFiltersEnable = clearFiltersEnable == null ? globalConfig.getClearFiltersEnable() : clearFiltersEnable;
        if (tempClearFiltersEnable != null && tempClearFiltersEnable.equals(Boolean.FALSE)) {
            properties.setProperty("druid.clearFiltersEnable", "false");
        }

        //
        Boolean tempResetStatEnable = resetStatEnable == null ? globalConfig.getResetStatEnable() : resetStatEnable;
        if (tempResetStatEnable != null && tempResetStatEnable.equals(Boolean.FALSE)) {
            properties.setProperty("druid.resetStatEnable", "false");
        }

        //
        Integer tempNotFullTimeoutRetryCount = notFullTimeoutRetryCount == null ? globalConfig.getNotFullTimeoutRetryCount() : notFullTimeoutRetryCount;
        if (tempNotFullTimeoutRetryCount != null && !tempNotFullTimeoutRetryCount.equals(0)) {
            properties.setProperty("druid.notFullTimeoutRetryCount", String.valueOf(tempNotFullTimeoutRetryCount));
        }

        //
        Integer tempMaxWaitThreadCount = maxWaitThreadCount == null ? globalConfig.getMaxWaitThreadCount() : maxWaitThreadCount;
        if (tempMaxWaitThreadCount != null && !tempMaxWaitThreadCount.equals(-1)) {
            properties.setProperty("druid.maxWaitThreadCount", String.valueOf(tempMaxWaitThreadCount));
        }

        //
        Boolean tempFailFast = failFast == null ? globalConfig.getFailFast() : failFast;
        if (tempFailFast != null && tempFailFast.equals(Boolean.TRUE)) {
            properties.setProperty("druid.failFast", "true");
        }

        // 连接最大存活时间，默认是-1(不限制物理连接时间)，从创建连接开始计算，如果超过该时间，则会被清理
        Long tempPhyTimeoutMillis = phyTimeoutMillis == null ? globalConfig.getPhyTimeoutMillis() : phyTimeoutMillis;
        if (tempPhyTimeoutMillis != null && !tempPhyTimeoutMillis.equals(DEFAULT_PHY_TIMEOUT_MILLIS)) {
            properties.setProperty("druid.phyTimeoutMillis", String.valueOf(tempPhyTimeoutMillis));
        }
        // TODO: 2019/1/17  druid.phyMaxUseCount

        //
        Boolean tempKeepAlive = keepAlive == null ? globalConfig.getKeepAlive() : keepAlive;
        if (tempKeepAlive != null && tempKeepAlive.equals(Boolean.TRUE)) {
            properties.setProperty("druid.keepAlive", "true");
        }

        //
        Boolean tempPoolPreparedStatements = poolPreparedStatements == null ? globalConfig.getPoolPreparedStatements() : poolPreparedStatements;
        if (tempPoolPreparedStatements != null && tempPoolPreparedStatements.equals(Boolean.TRUE)) {
            properties.setProperty("druid.poolPreparedStatements", "true");
        }

        //
        Boolean tempInitVariants = initVariants == null ? globalConfig.getInitVariants() : initVariants;
        if (tempInitVariants != null && tempInitVariants.equals(Boolean.TRUE)) {
            properties.setProperty("druid.initVariants", "true");
        }

        //
        Boolean tempInitGlobalVariants = initGlobalVariants == null ? globalConfig.getInitGlobalVariants() : initGlobalVariants;
        if (tempInitGlobalVariants != null && tempInitGlobalVariants.equals(Boolean.TRUE)) {
            properties.setProperty("druid.initGlobalVariants", "true");
        }

        //
        Boolean tempUseUnfairLock = useUnfairLock == null ? globalConfig.getUseUnfairLock() : useUnfairLock;
        if (tempUseUnfairLock != null) {
            properties.setProperty("druid.useUnfairLock", String.valueOf(tempUseUnfairLock));
        }

        //
        Boolean tempKillWhenSocketReadTimeout = killWhenSocketReadTimeout == null ? globalConfig.getKillWhenSocketReadTimeout() : killWhenSocketReadTimeout;
        if (tempKillWhenSocketReadTimeout != null && tempKillWhenSocketReadTimeout.equals(Boolean.TRUE)) {
            properties.setProperty("druid.killWhenSocketReadTimeout", "true");
        }

        //
        Integer tempMaxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize == null ? globalConfig.getMaxPoolPreparedStatementPerConnectionSize() : maxPoolPreparedStatementPerConnectionSize;
        if (tempMaxPoolPreparedStatementPerConnectionSize != null && !tempMaxPoolPreparedStatementPerConnectionSize.equals(10)) {
            properties.setProperty("druid.maxPoolPreparedStatementPerConnectionSize", String.valueOf(tempMaxPoolPreparedStatementPerConnectionSize));
        }

        //
        String tempInitConnectionSqls = initConnectionSqls == null ? globalConfig.getInitConnectionSqls() : initConnectionSqls;
        if (tempInitConnectionSqls != null && tempInitConnectionSqls.length() > 0) {
            properties.setProperty("druid.initConnectionSqls", tempInitConnectionSqls);
        }
        return properties;
    }
}
