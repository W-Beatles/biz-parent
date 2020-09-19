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
package cn.waynechu.bootstarter.dynamicdatasource.constant;

/**
 * @author zhuwei
 * @since 2020-03-19 22:46
 */
public class DruidConst {

    public static final String INITIAL_SIZE = "druid.initialSize";
    public static final String MAX_ACTIVE = "druid.maxActive";
    public static final String MIN_IDLE = "druid.minIdle";
    public static final String MAX_WAIT = "druid.maxWait";

    public static final String TIME_BETWEEN_EVICTION_RUNS_MILLIS = "druid.timeBetweenEvictionRunsMillis";
    public static final String TIME_BETWEEN_LOG_STATS_MILLIS = "druid.timeBetweenLogStatsMillis";
    public static final String MIN_EVICTABLE_IDLE_TIME_MILLIS = "druid.minEvictableIdleTimeMillis";
    public static final String MAX_EVICTABLE_IDLE_TIME_MILLIS = "druid.maxEvictableIdleTimeMillis";

    public static final String TEST_WHILE_IDLE = "druid.testWhileIdle";
    public static final String TEST_ON_BORROW = "druid.testOnBorrow";
    public static final String VALIDATION_QUERY = "druid.validationQuery";
    public static final String USE_GLOBAL_DATA_SOURCE_STAT = "druid.useGlobalDataSourceStat";
    public static final String ASYNC_INIT = "druid.asyncInit";

    public static final String FILTERS = "druid.filters";
    public static final String CLEAR_FILTERS_ENABLE = "druid.clearFiltersEnable";
    public static final String RESET_STAT_ENABLE = "druid.resetStatEnable";
    public static final String NOT_FULL_TIMEOUT_RETRY_COUNT = "druid.notFullTimeoutRetryCount";
    public static final String MAX_WAIT_THREAD_COUNT = "druid.maxWaitThreadCount";

    public static final String FAIL_FAST = "druid.failFast";
    public static final String PHY_TIMEOUT_MILLIS = "druid.phyTimeoutMillis";
    public static final String KEEP_ALIVE = "druid.keepAlive";
    public static final String POOL_PREPARED_STATEMENTS = "druid.poolPreparedStatements";
    public static final String INIT_VARIANTS = "druid.initVariants";
    public static final String INIT_GLOBAL_VARIANTS = "druid.initGlobalVariants";
    public static final String USE_UNFAIR_LOCK = "druid.useUnfairLock";
    public static final String KILL_WHEN_SOCKET_READ_TIMEOUT = "druid.killWhenSocketReadTimeout";
    public static final String MAX_POOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE = "druid.maxPoolPreparedStatementPerConnectionSize";
    public static final String INIT_CONNECTION_SQLS = "druid.initConnectionSqls";

    public static final String STAT_SQL_MAX_SIZE = "druid.stat.sql.MaxSize";
    public static final String STAT_LOG_SLOW_SQL = "druid.stat.logSlowSql";
    public static final String STAT_SLOW_SQL_MILLIS = "druid.stat.slowSqlMillis";
    public static final String STAT_MERGE_SQL = "druid.stat.mergeSql";
}
