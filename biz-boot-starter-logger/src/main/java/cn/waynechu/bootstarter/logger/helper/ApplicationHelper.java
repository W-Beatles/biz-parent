package cn.waynechu.bootstarter.logger.helper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhuwei
 * @date 2019/9/26 15:43
 */
@Slf4j
@Component
public class ApplicationHelper {

    private static String hostAddress = "-";
    private static String hostName = "-";
    private static String applicationName = "-";
    private static String parentProjectVersion = "-";

    public ApplicationHelper(@Value("${spring.application.name:-}") String applicationName,
                             @Value("${biz.logger.version.parent-project:-}") String parentProjectVersion) {
        try {
            ApplicationHelper.applicationName = applicationName;
            InetAddress address = InetAddress.getLocalHost();
            ApplicationHelper.hostAddress = address.getHostAddress();
            ApplicationHelper.hostName = address.getHostName();
            ApplicationHelper.parentProjectVersion = parentProjectVersion;
        } catch (UnknownHostException e) {
            // do nothing here.
        }
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static String getHostName() {
        return hostName;
    }

    public static String getApplicationName() {
        return applicationName;
    }

    public static String getParentProjectVersion() {
        return parentProjectVersion;
    }

    public static String getClientAppId() {
        return System.getProperty("java._appid_", "-");
    }
}
