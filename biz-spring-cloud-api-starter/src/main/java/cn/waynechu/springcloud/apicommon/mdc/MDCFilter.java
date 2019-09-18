package cn.waynechu.springcloud.apicommon.mdc;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.WebUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * MDC过滤器
 *
 * <pre>
 * 该过滤器会调用 MDC.put("reqKey", reqKeyValue) 添加reqKey到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息
 * 其中reqKey的格式为: traceNo-prefix-shortUUID
 * - traceNo: 请求的追踪号。可添加至请求头或请求参数中来追踪请求链路，其中key为traceNo
 * - prefix: 项目唯一标识
 * - shortUUID: 请求的唯一标识
 * </pre>
 *
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
@Slf4j
public class MDCFilter implements Filter {

    public static final String TRACE_NO_FLAG = "traceNo";
    public static final String API_VERSION_FLAG = "apiVersion";
    public static final String CHANNEL_FLAG = "channel";
    public static final String DEVICE_ID_FLAG = "deviceId";
    public static final String REQ_KEY = "reqKey";
    public static final String REQ_UUID = "reqUUID";
    public static final String APPLICATION_NAME = "applicationName";

    private String prefix;

    private String applicationName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            this.initMDC((HttpServletRequest) request);

            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private void initMDC(HttpServletRequest request) {
        String apiVersion = WebUtil.getReqParam(API_VERSION_FLAG, request);
        if (StringUtil.isNotBlank(apiVersion)) {
            MDC.put(API_VERSION_FLAG, apiVersion);
        }

        String channel = WebUtil.getReqParam(CHANNEL_FLAG, request);
        if (StringUtil.isNotBlank(channel)) {
            MDC.put(CHANNEL_FLAG, channel);
        }

        String deviceId = WebUtil.getReqParam(DEVICE_ID_FLAG, request);
        if (StringUtil.isNotBlank(deviceId)) {
            MDC.put(DEVICE_ID_FLAG, deviceId);
        }

        String traceNo = WebUtil.getReqParam(TRACE_NO_FLAG, request);
        String shortUuid = generateShortUuid();
        String reqKeyStr = generateReqKeyStr(traceNo, prefix, shortUuid);

        MDC.put(APPLICATION_NAME, applicationName);
        MDC.put(REQ_UUID, shortUuid);
        MDC.put(REQ_KEY, reqKeyStr);
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
    private static String generateShortUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }
}
