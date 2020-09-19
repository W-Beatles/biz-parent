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
package cn.waynechu.bootstarter.dynamicdatasource.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static cn.waynechu.bootstarter.dynamicdatasource.constant.DruidConst.*;
import static com.alibaba.druid.pool.DruidAbstractDataSource.*;

/**
 * Druid数据源配置
 *
 * @author zhuwei
 * @since 2019/1/15 15:50
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
    private Boolean removeAbandoned;
    private Integer removeAbandonedTimeoutMillis;
    private Boolean logAbandoned;
    private String publicKey;

    @NestedConfigurationProperty
    private DruidWallConfig wall = new DruidWallConfig();

    @NestedConfigurationProperty
    private DruidStatConfig stat = new DruidStatConfig();

    @NestedConfigurationProperty
    private DruidSlf4jConfig slf4j = new DruidSlf4jConfig();

    private List<String> proxyFilters = new ArrayList<>();

    public Properties toProperties(DruidConfig globalConfig) {
        Properties properties = new Properties();
        // 配置初始化大小、最小、最大
        Integer initialSize = this.initialSize == null ? globalConfig.getInitialSize() : this.initialSize;
        if (initialSize != null && !initialSize.equals(DEFAULT_INITIAL_SIZE)) {
            properties.setProperty(INITIAL_SIZE, String.valueOf(initialSize));
        }
        Integer maxActive = this.maxActive == null ? globalConfig.getMaxActive() : this.maxActive;
        if (maxActive != null && !maxActive.equals(DEFAULT_MAX_ACTIVE_SIZE)) {
            properties.setProperty(MAX_ACTIVE, String.valueOf(maxActive));
        }
        Integer minIdle = this.minIdle == null ? globalConfig.getMinIdle() : this.minIdle;
        if (minIdle != null && !minIdle.equals(DEFAULT_MIN_IDLE)) {
            properties.setProperty(MIN_IDLE, String.valueOf(minIdle));
        }

        // 配置获取连接等待超时的时间
        Integer maxWait = this.maxWait == null ? globalConfig.getMaxWait() : this.maxWait;
        if (maxWait != null && !maxWait.equals(DEFAULT_MAX_WAIT)) {
            properties.setProperty(MAX_WAIT, String.valueOf(maxWait));
        }

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        Long timeBetweenEvictionRunsMillis = this.timeBetweenEvictionRunsMillis == null ? globalConfig.getTimeBetweenEvictionRunsMillis() : this.timeBetweenEvictionRunsMillis;
        if (timeBetweenEvictionRunsMillis != null && !timeBetweenEvictionRunsMillis.equals(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS)) {
            properties.setProperty(TIME_BETWEEN_EVICTION_RUNS_MILLIS, String.valueOf(timeBetweenEvictionRunsMillis));
        }

        // 如果配置>0，druid会定期把监控数据输出到日志中
        Long timeBetweenLogStatsMillis = this.timeBetweenLogStatsMillis == null ? globalConfig.getTimeBetweenLogStatsMillis() : this.timeBetweenLogStatsMillis;
        if (timeBetweenLogStatsMillis != null && timeBetweenLogStatsMillis > 0) {
            properties.setProperty(TIME_BETWEEN_LOG_STATS_MILLIS, String.valueOf(timeBetweenLogStatsMillis));
        }

        // 配置一个连接在池中最小/最大生存的时间，单位是毫秒
        Long minEvictableIdleTimeMillis = this.minEvictableIdleTimeMillis == null ? globalConfig.getMinEvictableIdleTimeMillis() : this.minEvictableIdleTimeMillis;
        if (minEvictableIdleTimeMillis != null && !minEvictableIdleTimeMillis.equals(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS)) {
            properties.setProperty(MIN_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(minEvictableIdleTimeMillis));
        }
        Long maxEvictableIdleTimeMillis = this.maxEvictableIdleTimeMillis == null ? globalConfig.getMaxEvictableIdleTimeMillis() : this.maxEvictableIdleTimeMillis;
        if (maxEvictableIdleTimeMillis != null && !maxEvictableIdleTimeMillis.equals(DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS)) {
            properties.setProperty(MAX_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(maxEvictableIdleTimeMillis));
        }

        // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
        // 建议配置为true，不影响性能，并且保证安全性
        Boolean testWhileIdle = this.testWhileIdle == null ? globalConfig.getTestWhileIdle() : this.testWhileIdle;
        if (testWhileIdle != null && !testWhileIdle.equals(DEFAULT_WHILE_IDLE)) {
            properties.setProperty(TEST_WHILE_IDLE, "false");
        }
        // 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        Boolean testOnBorrow = this.testOnBorrow == null ? globalConfig.getTestOnBorrow() : this.testOnBorrow;
        if (testOnBorrow != null && !testOnBorrow.equals(DEFAULT_TEST_ON_BORROW)) {
            properties.setProperty(TEST_ON_BORROW, "true");
        }

        // 用来检测连接是否有效的sql，要求是一个查询语句
        // 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用
        String validationQuery = this.validationQuery == null ? globalConfig.getValidationQuery() : this.validationQuery;
        if (validationQuery != null && validationQuery.length() > 0) {
            properties.setProperty(VALIDATION_QUERY, validationQuery);
        }

        // 合并多个DruidDataSource的监控数据，缺省多个DruidDataSource的监控数据是各自独立的
        Boolean useGlobalDataSourceStat = this.useGlobalDataSourceStat == null ? globalConfig.getUseGlobalDataSourceStat() : this.useGlobalDataSourceStat;
        if (useGlobalDataSourceStat != null && useGlobalDataSourceStat.equals(Boolean.TRUE)) {
            properties.setProperty(USE_GLOBAL_DATA_SOURCE_STAT, "true");
        }

        // 如果有initialSize数量较多时，打开会加快应用启动时间
        Boolean asyncInit = this.asyncInit == null ? globalConfig.getAsyncInit() : this.asyncInit;
        if (asyncInit != null && asyncInit.equals(Boolean.TRUE)) {
            properties.setProperty(ASYNC_INIT, "true");
        }

        // filters单独处理，默认了stat,wall
        String filters = this.filters == null ? globalConfig.getFilters() : this.filters;
        if (filters == null) {
            filters = "stat,wall";
        }
        String publicKey = this.publicKey == null ? globalConfig.getPublicKey() : this.publicKey;
        if (publicKey != null && publicKey.length() > 0 && !filters.contains("config")) {
            filters += ",config";
        }
        properties.setProperty(FILTERS, filters);

        // 允许清除监控数据
        Boolean clearFiltersEnable = this.clearFiltersEnable == null ? globalConfig.getClearFiltersEnable() : this.clearFiltersEnable;
        if (clearFiltersEnable != null && clearFiltersEnable.equals(Boolean.FALSE)) {
            properties.setProperty(CLEAR_FILTERS_ENABLE, "false");
        }

        // 允许重置监控数据
        Boolean resetStatEnable = this.resetStatEnable == null ? globalConfig.getResetStatEnable() : this.resetStatEnable;
        if (resetStatEnable != null && resetStatEnable.equals(Boolean.FALSE)) {
            properties.setProperty(RESET_STAT_ENABLE, "false");
        }

        // 设置获取连接时的重试次数，-1为不重试
        Integer notFullTimeoutRetryCount = this.notFullTimeoutRetryCount == null ? globalConfig.getNotFullTimeoutRetryCount() : this.notFullTimeoutRetryCount;
        if (notFullTimeoutRetryCount != null && !notFullTimeoutRetryCount.equals(0)) {
            properties.setProperty(NOT_FULL_TIMEOUT_RETRY_COUNT, String.valueOf(notFullTimeoutRetryCount));
        }

        // 如果等待创建连接的线程数如果大于maxWaitThreadCount会抛出异常
        Integer maxWaitThreadCount = this.maxWaitThreadCount == null ? globalConfig.getMaxWaitThreadCount() : this.maxWaitThreadCount;
        if (maxWaitThreadCount != null && !maxWaitThreadCount.equals(-1)) {
            properties.setProperty(MAX_WAIT_THREAD_COUNT, String.valueOf(maxWaitThreadCount));
        }

        // 设置获取连接出错时是否马上返回错误，true为马上返回
        Boolean failFast = this.failFast == null ? globalConfig.getFailFast() : this.failFast;
        if (failFast != null && failFast.equals(Boolean.TRUE)) {
            properties.setProperty(FAIL_FAST, "true");
        }

        // 连接不管是否空闲，存活phyTimeoutMillis后强制回收
        Long phyTimeoutMillis = this.phyTimeoutMillis == null ? globalConfig.getPhyTimeoutMillis() : this.phyTimeoutMillis;
        if (phyTimeoutMillis != null && !phyTimeoutMillis.equals(DEFAULT_PHY_TIMEOUT_MILLIS)) {
            properties.setProperty(PHY_TIMEOUT_MILLIS, String.valueOf(phyTimeoutMillis));
        }

        // 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作
        Boolean keepAlive = this.keepAlive == null ? globalConfig.getKeepAlive() : this.keepAlive;
        if (keepAlive != null && keepAlive.equals(Boolean.TRUE)) {
            properties.setProperty(KEEP_ALIVE, "true");
        }

        // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        Boolean poolPreparedStatements = this.poolPreparedStatements == null ? globalConfig.getPoolPreparedStatements() : this.poolPreparedStatements;
        if (poolPreparedStatements != null && poolPreparedStatements.equals(Boolean.TRUE)) {
            properties.setProperty(POOL_PREPARED_STATEMENTS, "true");
        }

        // ..?
        Boolean initVariants = this.initVariants == null ? globalConfig.getInitVariants() : this.initVariants;
        if (initVariants != null && initVariants.equals(Boolean.TRUE)) {
            properties.setProperty(INIT_VARIANTS, "true");
        }
        Boolean initGlobalVariants = this.initGlobalVariants == null ? globalConfig.getInitGlobalVariants() : this.initGlobalVariants;
        if (initGlobalVariants != null && initGlobalVariants.equals(Boolean.TRUE)) {
            properties.setProperty(INIT_GLOBAL_VARIANTS, "true");
        }

        // 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        Boolean useUnfairLock = this.useUnfairLock == null ? globalConfig.getUseUnfairLock() : this.useUnfairLock;
        if (useUnfairLock != null) {
            properties.setProperty(USE_UNFAIR_LOCK, String.valueOf(useUnfairLock));
        }

        // socket连接超时断开连接
        Boolean killWhenSocketReadTimeout = this.killWhenSocketReadTimeout == null ? globalConfig.getKillWhenSocketReadTimeout() : this.killWhenSocketReadTimeout;
        if (killWhenSocketReadTimeout != null && killWhenSocketReadTimeout.equals(Boolean.TRUE)) {
            properties.setProperty(KILL_WHEN_SOCKET_READ_TIMEOUT, "true");
        }

        // 数据源密码加密
        Properties connectProperties = connectionProperties == null ? globalConfig.getConnectionProperties() : connectionProperties;
        if (publicKey != null && publicKey.length() > 0) {
            if (connectProperties == null) {
                connectProperties = new Properties();
            }
            log.info("The Druid datasource password is encrypted");
            connectProperties.setProperty("config.decrypt", "true");
            connectProperties.setProperty("config.decrypt.key", publicKey);
        }
        this.connectionProperties = connectProperties;

        // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true
        // 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        Integer maxPoolPreparedStatementPerConnectionSize = this.maxPoolPreparedStatementPerConnectionSize == null ? globalConfig.getMaxPoolPreparedStatementPerConnectionSize() : this.maxPoolPreparedStatementPerConnectionSize;
        if (maxPoolPreparedStatementPerConnectionSize != null && !maxPoolPreparedStatementPerConnectionSize.equals(10)) {
            properties.setProperty(MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE, String.valueOf(maxPoolPreparedStatementPerConnectionSize));
        }

        // 初始化连接的时候执行的sql
        String initConnectionSqls = this.initConnectionSqls == null ? globalConfig.getInitConnectionSqls() : this.initConnectionSqls;
        if (initConnectionSqls != null && initConnectionSqls.length() > 0) {
            properties.setProperty(INIT_CONNECTION_SQLS, initConnectionSqls);
        }

        // stat监控配置参数
        Integer statSqlMaxSize = this.statSqlMaxSize == null ? globalConfig.getStatSqlMaxSize() : this.statSqlMaxSize;
        if (statSqlMaxSize != null) {
            properties.setProperty(STAT_SQL_MAX_SIZE, String.valueOf(statSqlMaxSize));
        }
        Boolean logSlowSql = stat.getLogSlowSql() == null ? globalConfig.stat.getLogSlowSql() : stat.getLogSlowSql();
        if (logSlowSql != null && logSlowSql) {
            properties.setProperty(STAT_LOG_SLOW_SQL, "true");
        }
        Long slowSqlMillis = stat.getSlowSqlMillis() == null ? globalConfig.stat.getSlowSqlMillis() : stat.getSlowSqlMillis();
        if (slowSqlMillis != null) {
            properties.setProperty(STAT_SLOW_SQL_MILLIS, slowSqlMillis.toString());
        }
        Boolean mergeSql = stat.getMergeSql() == null ? globalConfig.stat.getMergeSql() : stat.getMergeSql();
        if (mergeSql != null && mergeSql) {
            properties.setProperty(STAT_MERGE_SQL, "true");
        }

        return properties;
    }
}
