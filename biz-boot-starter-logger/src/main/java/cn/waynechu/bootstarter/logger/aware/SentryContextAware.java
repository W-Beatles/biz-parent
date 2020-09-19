package cn.waynechu.bootstarter.logger.aware;

import cn.waynechu.springcloud.common.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;

/**
 * @author zhuwei
 * @since 2019/1/8 13:26
 */
public class SentryContextAware implements ApplicationContextAware, PriorityOrdered {
    public static final String SENTRY_ENABLE = "sentry.enable";
    private static final String SENTRY_DSN = "sentry.dsn";
    private static final String TRUE = "true";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment environment = applicationContext.getEnvironment();
        String sentryEnable = environment.getProperty(SENTRY_ENABLE);
        String sentryDsn = environment.getProperty(SENTRY_DSN);
        if (TRUE.equalsIgnoreCase(sentryEnable) && StringUtil.isBlank(sentryDsn)) {
            throw new RuntimeException("sentry.dsn未配置!");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
