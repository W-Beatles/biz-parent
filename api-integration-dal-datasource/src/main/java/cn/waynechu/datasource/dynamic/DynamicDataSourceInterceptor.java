package cn.waynechu.datasource.dynamic;

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
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @author zhuwei
 * @date 2018/11/7 14:12
 */
@Component
@Intercepts({
        @Signature(
                type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(
                type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 当前有没有开启事务
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        String lookUpKey = null;
        if (!synchronizationActive) {
            // 当前查询不在事务管理状态下
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookUpKey = DynamicDataSourceHolder.DATASOURCE_TYPE_MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
                    // 将sql中的制表符、回车符、换行符替换成空格
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("\\t\\r\\n", " ");
                    if (sql.matches(REGEX)) {
                        lookUpKey = DynamicDataSourceHolder.DATASOURCE_TYPE_MASTER;
                    } else {
                        lookUpKey = DynamicDataSourceHolder.DATASOURCE_TYPE_SALVE;
                    }
                }
            }
            logger.info("[DynamicDataSourceInterceptor] determine to use datasource [{}] ,method [{}], SqlCommandType [{}]", lookUpKey, ms.getId(), ms.getSqlCommandType().name());
        } else {
            lookUpKey = DynamicDataSourceHolder.DATASOURCE_TYPE_MASTER;
            logger.info("[DynamicDataSourceInterceptor] determine to use datasource [{}]  with transition", lookUpKey);
        }
        DynamicDataSourceHolder.setDataSourceType(lookUpKey);
        return invocation.proceed();
    }

    /**
     * 返回本体还是代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        // do nothing here
    }
}
