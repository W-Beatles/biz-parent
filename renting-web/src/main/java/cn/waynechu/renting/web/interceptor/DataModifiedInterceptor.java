package cn.waynechu.renting.web.interceptor;


import cn.waynechu.common.bean.BeanUtil;
import cn.waynechu.webcommon.session.SessionHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * @author zhuwei
 * @date 2018/12/14 17:56
 */
@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class DataModifiedInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        BoundSql boundSql = ms.getSqlSource().getBoundSql(args[1]);
        Object parameterObject = boundSql.getParameterObject();

        if (ms.getSqlCommandType().equals(SqlCommandType.INSERT)) {
            BeanUtil.setPropertyValue(parameterObject, "createdUser", SessionHolder.getAccountSession().getUserName());
            BeanUtil.setPropertyValue(parameterObject, "createdTime", new Date());
        } else if (ms.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
            BeanUtil.setPropertyValue(parameterObject, "updatedUser", SessionHolder.getAccountSession().getUserName());
            BeanUtil.setPropertyValue(parameterObject, "updatedTime", new Date());
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

