package cn.waynechu.boot.starter.common.filter;

import cn.waynechu.webcommon.util.StringUtil;
import cn.waynechu.webcommon.util.WebUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * MDC过滤器
 *
 * <pre>
 * 该过滤器会调用 MDC.put("reqKey", reqKeyValue) 添加reqKey到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息
 * 其中reqKey的格式为: traceNo-prefix-shortJavaUUID-localHostName
 * - traceNo: 请求的追踪号。可添加至请求头或请求参数中来追踪请求链路，其中key为traceNo
 * - prefix: 项目唯一标识
 * - shortJavaUUID: 请求的唯一标识
 * - localHostName: 服务器HostName
 * </pre>
 *
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
@Slf4j
public class MDCFilter implements Filter {
    private static final String TRACE_NO_FLAG = "traceNo";
    private static final String REQ_KEY = "reqKey";
    public static final String APPLICATION_NAME = "applicationName";

    private String prefix;

    private String applicationName;

    private static String localHostName;

    static {
        try {
            localHostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("获取本机HostName异常:{}", e.getMessage(), e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String traceNo = WebUtil.getReqParam(TRACE_NO_FLAG, (HttpServletRequest) request);
            this.initMDC(traceNo);

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private void initMDC(String traceNo) {
        String shortJavaUUID = generateShortJavaUUID();
        String reqKeyStr = generateReqKeyStr(traceNo, prefix, shortJavaUUID, localHostName);

        MDC.put(REQ_KEY, reqKeyStr);
        MDC.put(APPLICATION_NAME, applicationName);
    }

    private static String generateReqKeyStr(String... items) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < items.length - 1; i++) {
            if (StringUtil.isNotEmpty(items[i])) {
                builder.append(items[i]).append("-");
            }
        }
        String lastItem = items[items.length - 1];
        if (StringUtil.isNotEmpty(lastItem)) {
            builder.append(lastItem);
        }
        return builder.toString();
    }

    /**
     * 生成随机UUID的前6位
     * <p>
     * 10000次内约有五次左右重复
     *
     * @return uuid
     */
    private static String generateShortJavaUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }
}
