package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * @author zhuwei
 * @date 2018/11/6 20:42
 */
@UtilityClass
public class IpUtil {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request req
     * @return ip
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String remoteIp = null;
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        remoteIp = request.getRemoteAddr();

        // 多级反向代理
        if (null != remoteIp && !"".equals(remoteIp.trim())) {
            StringTokenizer st = new StringTokenizer(remoteIp, ",");
            String ipTmp = "";

            if (st.countTokens() > 1) {
                while (st.hasMoreTokens()) {
                    ipTmp = st.nextToken();
                    if (ipTmp != null && ipTmp.length() != 0 && !"unknown".equalsIgnoreCase(ipTmp)) {
                        remoteIp = ipTmp;
                        break;
                    }
                }
            }
        }
        return remoteIp;
    }
}
