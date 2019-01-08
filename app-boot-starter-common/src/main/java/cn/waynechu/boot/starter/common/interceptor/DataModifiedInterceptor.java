package cn.waynechu.boot.starter.common.interceptor;


import cn.waynechu.boot.starter.common.holder.IDataModifiedHolder;
import cn.waynechu.boot.starter.common.properties.DataModifiedProperties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * @author zhuwei
 * @date 2018/12/14 17:56
 */
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class DataModifiedInterceptor implements Interceptor {

    private IDataModifiedHolder dataModifiedHolder;

    private DataModifiedProperties dataModifiedProperties;

    public DataModifiedInterceptor(IDataModifiedHolder dataModifiedHolder, DataModifiedProperties dataModifiedProperties) {
        this.dataModifiedHolder = dataModifiedHolder;
        this.dataModifiedProperties = dataModifiedProperties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        Class<?> parameterObjectClass = parameterObject.getClass();

        if (SqlCommandType.INSERT.equals(ms.getSqlCommandType())) {
            Field createdUser = parameterObjectClass.getDeclaredField(dataModifiedProperties.getCreatedUserAttrName());
            Field createdTime = parameterObjectClass.getDeclaredField(dataModifiedProperties.getCreatedTimeAttrName());

            createdUser.setAccessible(true);
            createdTime.setAccessible(true);

            createdUser.set(parameterObject, dataModifiedHolder.getModifierName());
            createdTime.set(parameterObject, new Date());
        } else if (SqlCommandType.UPDATE.equals(ms.getSqlCommandType())) {
            Field updatedUser = parameterObjectClass.getDeclaredField(dataModifiedProperties.getUpdatedUserAttrName());
            Field updatedTime = parameterObjectClass.getDeclaredField(dataModifiedProperties.getUpdatedTimeAttrName());

            updatedUser.setAccessible(true);
            updatedTime.setAccessible(true);

            updatedUser.set(parameterObject, dataModifiedHolder.getModifierName());
            updatedTime.set(parameterObject, new Date());
        }
        return invocation.proceed();
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

