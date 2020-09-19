package cn.waynechu.bootstarter.logger.provider;


import cn.waynechu.springcloud.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author zhuwei
 * @since 2019/9/26 15:43
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationProvider implements InitializingBean {

    private static String hostName = "-";
    private static String hostAddress = "-";

    private static String appId = "-";
    private static String appName = "-";
    private static String parentProjectVersion = "-";

    private Properties appProperties = new Properties();
    public static final String APP_PROPERTIES_CLASSPATH = "/META-INF/app.properties";

    public ApplicationProvider(@Value("${spring.application.name:-}") String applicationName,
                               @Value("${biz.logger.version.parent-project:-}") String parentProjectVersion) {
        try {
            ApplicationProvider.appName = applicationName;
            InetAddress address = InetAddress.getLocalHost();
            ApplicationProvider.hostName = address.getHostName();
            ApplicationProvider.hostAddress = address.getHostAddress();
            ApplicationProvider.parentProjectVersion = parentProjectVersion;
        } catch (UnknownHostException e) {
            // do nothing here.
        }
    }

    @Override
    public void afterPropertiesSet() {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_PROPERTIES_CLASSPATH);
            if (in == null) {
                in = ApplicationProvider.class.getResourceAsStream(APP_PROPERTIES_CLASSPATH);
            }

            appProperties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            this.initAppId();
        } catch (Exception ex) {
            log.error("Initialize DefaultApplicationProvider failed.", ex);
        }
    }

    private void initAppId() {
        // 1. Get app.id from System Property
        appId = System.getProperty("app.id");
        if (StringUtil.isNotBlank(appId)) {
            appId = appId.trim();
            log.info("App ID is set to {} by app.id property from System Property", appId);
            return;
        }

        // 2. Try to get app id from OS environment variable
        appId = System.getenv("APP_ID");
        if (StringUtil.isNotBlank(appId)) {
            appId = appId.trim();
            log.info("App ID is set to {} by APP_ID property from OS environment variable", appId);
            return;
        }

        // 3. Try to get app id from app.properties.
        appId = appProperties.getProperty("app.id");
        if (StringUtil.isNotBlank(appId)) {
            appId = appId.trim();
            log.info("App ID is set to {} by app.id property from {}", appId, APP_PROPERTIES_CLASSPATH);
            return;
        }

        appId = "-";
        log.warn("app.id is not available from System Property and {}. It is set to -", APP_PROPERTIES_CLASSPATH);
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getAppName() {
        return appName;
    }

    public static String getParentProjectVersion() {
        return parentProjectVersion;
    }

    public static String getAppId() {
        return appId;
    }

    public String getProperty(String name, String defaultValue) {
        if ("app.id".equals(name)) {
            String val = getAppId();
            return val == null ? defaultValue : val;
        } else {
            String val = appProperties.getProperty(name, defaultValue);
            return val == null ? defaultValue : val;
        }
    }
}
