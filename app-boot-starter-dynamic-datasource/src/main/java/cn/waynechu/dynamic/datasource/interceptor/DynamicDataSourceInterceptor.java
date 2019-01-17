/**
 * Copyright © 2018 organization waynechu
 * <pre>
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
 * <pre/>
 */
package cn.waynechu.dynamic.datasource.interceptor;

import cn.waynechu.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;

/**
 * 自定义Mybatis拦截器，用于动态数据源的选择
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

    private static final String QUERY_METHOD_NAME = "query";

    private static final String SQL_TYPE_SELECT_KEY = "SELECT_KEY";
    private static final String SQL_TYPE_READ_ONLY = "READ_ONLY";
    private static final String SQL_TYPE_TRANSITION = "TRANSITION";
    private static final String SQL_TYPE_DATA_MODIFY = "DATA_MODIFY";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String lookUpKey;
        String sqlType;

        if (QUERY_METHOD_NAME.equals(invocation.getMethod().getName())) {
            boolean isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();

            if (!isTransactionActive) {
                Object[] args = invocation.getArgs();
                MappedStatement ms = (MappedStatement) args[0];

                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    sqlType = SQL_TYPE_SELECT_KEY;
                    lookUpKey = DynamicDataSourceContextHolder.DATASOURCE_GROUP_MASTER_PREFIX;
                } else {
                    sqlType = SQL_TYPE_READ_ONLY;
                    lookUpKey = DynamicDataSourceContextHolder.DATASOURCE_GROUP_SALVE_PREFIX;
                }
            } else {
                sqlType = SQL_TYPE_TRANSITION;
                lookUpKey = DynamicDataSourceContextHolder.DATASOURCE_GROUP_MASTER_PREFIX;
            }
        } else {
            sqlType = SQL_TYPE_DATA_MODIFY;
            lookUpKey = DynamicDataSourceContextHolder.DATASOURCE_GROUP_MASTER_PREFIX;
        }

        logger.debug("SQL类型为 [{}]，将使用 [{}] 数据源", sqlType, lookUpKey);
        try {
            DynamicDataSourceContextHolder.push(lookUpKey);
            return invocation.proceed();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
    }

    /**
     * 使用MyBatis提供的Plugin类的wrap方法生成代理对象
     *
     * @param target 被代理对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 获取插件配置的属性
     *
     * @param properties MyBatis配置的参数
     */
    @Override
    public void setProperties(Properties properties) {
        // do nothing here.
    }
}
