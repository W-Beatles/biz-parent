package com.tuhu.shop.datasource.interceptor;

import com.tuhu.shop.datasource.dynamic.DataSourceTypeHolder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * 自定义Mybatis拦截器，用于动态数据源的选择
 * <p>
 * 自定义拦截器与PageHelper冲突导致拦截失效的问题可以参考：
 * Executor 拦截器高级教程 - QueryInterceptor 规范
 * https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
 *
 * @author zhuwei
 * @date 2018/11/6 13:12
 */
@Intercepts({
        @Signature(
                type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(
                type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 拦截的方法签名如下：
     * <E> List<E> query( MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;
     * <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = (Executor) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;

        if (args.length == 4) {
            // 拦截的方法为 4 个参数时
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            // 拦截的方法为 6 个参数时
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }

        // 判断是否开启事务
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        String lookUpKey = null;
        if (!synchronizationActive) {
            // 当前查询不在事务管理状态下
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookUpKey = DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
                } else {
                    // 将sql中的制表符、回车符、换行符替换成空格
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("\\t\\r\\n", " ");
                    if (sql.matches(REGEX)) {
                        lookUpKey = DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
                    } else {
                        lookUpKey = DataSourceTypeHolder.DATASOURCE_TYPE_SALVE;
                    }
                }
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Intercept and determine target dataSource [{}], method [{}], SqlCommandType [{}]", lookUpKey, ms.getId(), ms.getSqlCommandType().name());
            }
        } else {
            lookUpKey = DataSourceTypeHolder.DATASOURCE_TYPE_MASTER;
            logger.debug("Intercept and determine target dataSource [{}] for transaction", lookUpKey);
        }

        DataSourceTypeHolder.setDataSourceType(lookUpKey);
        return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }
}
