package cn.waynechu.springcloud.gateway.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 获取reactive.ServerHttpRequest ip，可穿透代理
 *
 * @author zhuwei
 * @since 2020-03-07 23:57
 */
@UtilityClass
public class IpReactiveUtils {

    public static final String DEFAULT_IP = "unknown";

    /**
     * 获取ip地址
     *
     * @param request ServerHttpRequest
     * @return ip地址 可能返回 unknown
     */
    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> ips = headers.get("x-real-ip");
        String ip = DEFAULT_IP;

        if (!CollectionUtils.isEmpty(ips)) {
            ip = ips.get(0);
        }

        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("x-forwarded-for");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0).split(",")[0].trim();
            }
        }

        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("Proxy-Client-IP");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }

        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("WL-Proxy-Client-IP");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }

        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_X_FORWARDED_FOR");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_X_FORWARDED");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_X_CLUSTER_CLIENT_IP");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_CLIENT_IP");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_FORWARDED_FOR");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_FORWARDED");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("HTTP_VIA");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }
        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            ips = headers.get("REMOTE_ADDR");
            if (!CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0);
            }
        }

        if (ip == null || ip.length() == 0 || DEFAULT_IP.equalsIgnoreCase(ip)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if (null != remoteAddress) {
                ip = remoteAddress.getAddress().getHostAddress();
            } else {
                ip = DEFAULT_IP;
            }
        }

        return ip;
    }
}
