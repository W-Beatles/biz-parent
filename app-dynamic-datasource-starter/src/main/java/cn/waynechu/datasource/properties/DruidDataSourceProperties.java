package cn.waynechu.datasource.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author zhuwei
 * @date 2018/11/7 14:29
 */
@ConfigurationProperties(DruidDataSourceProperties.PREFIX)
public class DruidDataSourceProperties {
    public static final String PREFIX = "spring.datasource.druid";

    /**
     * 主数据库地址
     */
    private String url;

    /**
     * 从数据库地址，多个请使用;分割
     */
    private String slaveUrls;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 用来解密的密码公钥
     */
    private String publicKey;

    /**
     * 初始化时建立物理连接的个数
     */
    private int initialSize = 5;

    /**
     * 最大连接池数量
     */
    private int maxActive = 50;

    /**
     * 空闲的最小连接数量, 相当于线程池的最小连接数
     */
    private int minIdle = 5;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁
     */
    private int maxWait = 60000;

    /**
     * Destroy线程会检测需要关闭空闲连接的时间间隔，单位毫秒，默认1分钟
     */
    private int timeBetweenEvictionRunsMillis = 60000;

    /**
     * 连接保持空闲而不被驱逐的最小时间，默认5分钟
     */
    private int minEvictableIdleTimeMillis = 300000;

    /**
     * 验证链接是否有效的sql。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery = "SELECT 'x'";

    /**
     * 检测连接是否有效的超时时间，单位秒
     */
    private int validationQueryTimeout;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnBorrow = false;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnReturn = false;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle，在mysql下建议关闭。
     */
    private boolean poolPreparedStatements = false;

    /**
     * poolPreparedStatements为false的情况，该值不起作用
     */
    private int maxOpenPreparedStatements = 20;

//    /**
//     * 使用的过滤器插件，常用的有：
//     * <p>
//     * 监控统计用的filter:stat
//     * 日志用的filter:log4j
//     * 防御sql注入的filter:wall
//     */
//    private String filters;

    /**
     * 当启用监控后, 是否打印慢sql
     */
    private boolean logSlowSql = true;

    /**
     * 多少毫秒的sql认为是慢sql, 默认1秒
     */
    private int slowSqlMillis = 1000;

    /**
     * 是否合并sql, 同一个PreparedStatements但where条件不同会被认为是一个sql
     */
    private boolean mergeSql = true;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getSlaveUrls() {
        Set<String> returnValue = new HashSet<>();

        if (!StringUtils.isEmpty(slaveUrls)) {
            final String[] urlsSplit = StringUtils.delimitedListToStringArray(slaveUrls, ";");

            returnValue = new HashSet<>(urlsSplit.length);
            returnValue.addAll(Arrays.asList(urlsSplit));
        }
        return returnValue;
    }

    public void setSlaveUrls(String slaveUrls) {
        this.slaveUrls = slaveUrls;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

//    public String getFilters() {
//        return filters;
//    }
//
//    public void setFilters(String filters) {
//        this.filters = filters;
//    }

    public boolean isLogSlowSql() {
        return logSlowSql;
    }

    public void setLogSlowSql(boolean logSlowSql) {
        this.logSlowSql = logSlowSql;
    }

    public int getSlowSqlMillis() {
        return slowSqlMillis;
    }

    public void setSlowSqlMillis(int slowSqlMillis) {
        this.slowSqlMillis = slowSqlMillis;
    }

    public boolean isMergeSql() {
        return mergeSql;
    }

    public void setMergeSql(boolean mergeSql) {
        this.mergeSql = mergeSql;
    }
}
