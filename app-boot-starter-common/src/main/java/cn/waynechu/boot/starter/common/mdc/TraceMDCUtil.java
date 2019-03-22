package cn.waynechu.boot.starter.common.mdc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Slf4j
public class TraceMDCUtil implements AutoCloseable {
    private static final String REQ_KEY = "reqKey";
    private static String localHostName = null;

    static {
        try {
            localHostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("获取本机HostName异常:{}", e.getMessage(), e);
        }
    }

    public static void addObjects(Object... objects) {
        if (objects == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (Object item : objects) {
            builder.append(item);
        }

        String currentMDC = MDC.get(REQ_KEY);
        if (StringUtils.isNotBlank(currentMDC)) {
            MDC.put(REQ_KEY, currentMDC + "-" + builder.toString());
        } else {
            MDC.put(REQ_KEY, builder.toString());
        }
    }

    public static void addLocalHostName() {
        String currentMDC = MDC.get(REQ_KEY);
        if (StringUtils.isNotBlank(localHostName) && (!currentMDC.contains(localHostName))) {
            if (StringUtils.isNotBlank(currentMDC)) {
                MDC.put(REQ_KEY, currentMDC + "-" + localHostName);
            } else {
                MDC.put(REQ_KEY, localHostName);
            }
        }
    }

    public static void clear() {
        MDC.clear();
    }

    @Override
    public void close() {
        MDC.clear();
    }
}
