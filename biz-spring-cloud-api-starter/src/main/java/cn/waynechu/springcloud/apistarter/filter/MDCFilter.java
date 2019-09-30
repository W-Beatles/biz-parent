package cn.waynechu.springcloud.apistarter.filter;

import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.util.MDCUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import cn.waynechu.springcloud.common.util.WebUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MDC过滤器
 * <p>
 * 该过滤器用于添加请求信息到MDC上下文中，并且添加header追踪信息
 * 如: 调用 MDC.put("traceNo", traceNoValue) 添加traceNo到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息
 *
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class MDCFilter extends OncePerRequestFilter {
    public static final String HEADER_KEY_TRACE_NO = "traceNo";
    public static final String HEADER_KEY_REQUEST_ID = "requestId";

    public static final String HEADER_KEY_API_VERSION = "apiVersion";
    public static final String HEADER_KEY_CHANNEL = "channel";
    public static final String HEADER_KEY_DEVICE_ID = "deviceId";

    public static final String HEADER_KEY_APP_ID = "appId";
    public static final String HEADER_KEY_APP_NAME = "appName";
    public static final String HEADER_KEY_HOST_NAME = "hostName";
    public static final String HEADER_KEY_HOST_ADDRESS = "hostAddress";

    public static final String HEADER_KEY_TRACE_APP_IDS = "traceAppIds";
    public static final String HEADER_KEY_TRACE_APP_NAMES = "traceAppNames";
    public static final String HEADER_KEY_TRACE_HOST_NAMES = "traceHostNames";
    public static final String HEADER_KEY_TRACE_HOST_ADDRESSES = "traceHostAddresses";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = this.modifyRequestAndAddMdc(request);

            filterChain.doFilter(modifyHttpServletRequestWrapper, response);
        } finally {
            MDC.clear();
        }
    }

    private ModifyHttpServletRequestWrapper modifyRequestAndAddMdc(HttpServletRequest request) {
        ModifyHttpServletRequestWrapper httpServletRequestWrapper = new ModifyHttpServletRequestWrapper(request);

        String requestId = WebUtil.getReqHeader(HEADER_KEY_REQUEST_ID, request);
        if (StringUtil.isBlank(requestId)) {
            requestId = UUIDUtil.getShortUUID();
            httpServletRequestWrapper.putHeader(HEADER_KEY_REQUEST_ID, requestId);
        }
        MDC.put(HEADER_KEY_REQUEST_ID, requestId);

        String appId = ApplicationProvider.getAppId();
        String traceAppIds = WebUtil.getReqHeader(HEADER_KEY_TRACE_APP_IDS, request);
        if (StringUtil.isNotBlank(traceAppIds)) {
            appId = traceAppIds + "," + appId;
        }
        MDC.put(HEADER_KEY_TRACE_APP_IDS, appId);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_APP_IDS, appId);

        String appName = ApplicationProvider.getAppName();
        String traceAppNames = WebUtil.getReqHeader(HEADER_KEY_TRACE_APP_NAMES, request);
        if (StringUtil.isNotBlank(traceAppNames)) {
            appName = traceAppNames + "," + appName;
        }
        MDC.put(HEADER_KEY_TRACE_APP_NAMES, appName);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_APP_NAMES, appName);

        String hostName = ApplicationProvider.getHostName();
        String traceHostNames = WebUtil.getReqHeader(HEADER_KEY_TRACE_HOST_NAMES, request);
        if (StringUtil.isNotBlank(traceHostNames)) {
            hostName = traceHostNames + "," + hostName;
        }
        MDC.put(HEADER_KEY_TRACE_HOST_NAMES, hostName);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_HOST_NAMES, hostName);

        String hostAddress = ApplicationProvider.getHostName();
        String traceHostAddress = WebUtil.getReqHeader(HEADER_KEY_TRACE_HOST_ADDRESSES, request);
        if (StringUtil.isNotBlank(traceHostAddress)) {
            hostAddress = traceHostAddress + "," + hostAddress;
        }
        MDC.put(HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress);

        MDCUtil.copyReqParamOrHeaderToMDC(httpServletRequestWrapper, HEADER_KEY_TRACE_NO);
        MDCUtil.copyReqHeaderToMDC(httpServletRequestWrapper, HEADER_KEY_API_VERSION, HEADER_KEY_CHANNEL, HEADER_KEY_DEVICE_ID);
        return httpServletRequestWrapper;
    }
}
