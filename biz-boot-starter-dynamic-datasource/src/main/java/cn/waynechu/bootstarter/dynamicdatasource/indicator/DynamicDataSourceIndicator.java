package cn.waynechu.bootstarter.dynamicdatasource.indicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源健康检查
 *
 * @author zhuwei
 * @date 2020/3/5 16:52
 */
public class DynamicDataSourceIndicator extends AbstractHealthIndicator {

    private static final String DEFAULT_QUERY = "SELECT 1";

    private DataSource dataSource;

    private String query;

    /**
     * 维护数据源健康状况
     */
    private static Map<String, Boolean> DB_HEALTH = new ConcurrentHashMap<>();

    public DynamicDataSourceIndicator(DataSource dataSource) {
        this(dataSource, null);
    }

    public DynamicDataSourceIndicator(DataSource dataSource, String query) {
        super("DataSource health check failed");
        this.dataSource = dataSource;
        this.query = query;
    }

    /**
     * 获取数据源连接健康状况
     *
     * @param dataSource 数据源名称
     * @return 健康状况
     */
    public static boolean getDbHealth(String dataSource) {
        return DB_HEALTH.get(dataSource);
    }

    /**
     * 设置连接池健康状况
     *
     * @param dataSource 数据源名称
     * @param health     健康状况 false 不健康 true 健康
     * @return 设置状态
     */
    public static Boolean setDbHealth(String dataSource, boolean health) {
        return DB_HEALTH.put(dataSource, health);
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
            String product = getProduct(dataSource);
            builder.up().withDetail("database", dataSourceEntry.getValue());
            builder.withDetail("type", product);
            String validationQuery = getValidationQuery(product);
            int result = 0;
            try {
                result = query(dataSource, validationQuery);
                builder.withDetail("result", result);
            } finally {
                DB_HEALTH.put(dataSourceEntry.getKey(), result == 1);
                builder.withDetail("validationQuery", validationQuery);
            }

    }

    private String getProduct(DataSource dataSource) {
        return new JdbcTemplate(dataSource).execute((ConnectionCallback<String>) this::getProduct);
    }

    private String getProduct(Connection connection) throws SQLException {
        return connection.getMetaData().getDatabaseProductName();
    }

    protected String getValidationQuery(String product) {
        String query = this.query;
        if (!StringUtils.hasText(query)) {
            DatabaseDriver specific = DatabaseDriver.fromProductName(product);
            query = specific.getValidationQuery();
        }
        if (!StringUtils.hasText(query)) {
            query = DEFAULT_QUERY;
        }
        return query;
    }

    private Integer query(DataSource dataSource, String query) {
        List<Integer> results = new JdbcTemplate(dataSource).query(query, (resultSet, i) -> {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            if (columns != 1) {
                throw new IncorrectResultSetColumnCountException(1, columns);
            }
            return (Integer) JdbcUtils.getResultSetValue(resultSet, 1, Integer.class);
        });
        return DataAccessUtils.requiredSingleResult(results);
    }
}
