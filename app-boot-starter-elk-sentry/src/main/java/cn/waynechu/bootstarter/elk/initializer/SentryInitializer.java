package cn.waynechu.bootstarter.elk.initializer;

import cn.waynechu.bootstarter.elk.properties.SentryProperties;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author zhuwei
 * @date 2019/1/8 13:26
 */
public class SentryInitializer implements InitializingBean {

    private SentryProperties sentryProperties;

    public SentryInitializer(SentryProperties sentryProperties) {
        this.sentryProperties = sentryProperties;
    }

    @Override
    public void afterPropertiesSet() {
        System.setProperty("sentry.dsn", sentryProperties.getDsn());
        System.setProperty("sentry.stacktrace.app.packages", sentryProperties.getStacktraceAppPackages());
    }
}
