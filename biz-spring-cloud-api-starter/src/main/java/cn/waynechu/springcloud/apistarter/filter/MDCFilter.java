package cn.waynechu.springcloud.apistarter.filter;

import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.util.MDCUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import cn.waynechu.springcloud.common.util.WebUtil;
import lombok.Data;
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
 * 该过滤器用于添加请求信息到MDC上下文中
 * 如: 调用 MDC.put("traceNo", traceNoValue) 添加traceNo到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息
 *
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
@Slf4j
public class MDCFilter extends OncePerRequestFilter {
    public static final String TRACE_NO_FLAG = "traceNo";
    public static final String REQUEST_ID_FLAG = "requestId";

    public static final String API_VERSION_FLAG = "apiVersion";
    public static final String CHANNEL_FLAG = "channel";
    public static final String DEVICE_ID_FLAG = "deviceId";

    public static final String APP_ID_FLAG = "appId";
    public static final String APP_NAME_FLAG = "appName";
    public static final String HOST_NAME_FLAG = "hostName";
    public static final String HOST_ADDRESS_FLAG = "hostAddress";

    public static final String TRACE_APP_IDS_FLAG = "traceAppIds";
    public static final String TRACE_APP_NAMES_FLAG = "traceAppNames";
    public static final String TRACE_HOST_NAMES_FLAG = "traceHostNames";
    public static final String TRACE_HOST_ADDRESSES_FLAG = "traceHostAddresses";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = this.initMDC(request);

            filterChain.doFilter(modifyHttpServletRequestWrapper, response);
        } finally {
            MDC.clear();
        }
    }

    private ModifyHttpServletRequestWrapper initMDC(HttpServletRequest request) {
        MDCUtil.copyReqParamOrHeaderToMDC(request, TRACE_NO_FLAG);
        MDCUtil.copyReqHeaderToMDC(request, REQUEST_ID_FLAG, API_VERSION_FLAG, CHANNEL_FLAG, DEVICE_ID_FLAG);

        ModifyHttpServletRequestWrapper httpServletRequestWrapper = new ModifyHttpServletRequestWrapper(request);

        String requestId = WebUtil.getReqHeader(REQUEST_ID_FLAG, request);
        if (StringUtil.isNotBlank(requestId)) {
            httpServletRequestWrapper.putHeader(REQUEST_ID_FLAG, UUIDUtil.getShortUUID());
        }

        String appId = ApplicationProvider.getAppId();
        String traceAppIds = WebUtil.getReqHeader(TRACE_APP_IDS_FLAG, request);
        if (StringUtil.isNotBlank(traceAppIds)) {
            appId = traceAppIds + "," + appId;
        }
        MDC.put(TRACE_APP_IDS_FLAG, appId);
        httpServletRequestWrapper.putHeader(TRACE_APP_IDS_FLAG, appId);

        String appName = ApplicationProvider.getAppName();
        String traceAppNames = WebUtil.getReqHeader(TRACE_APP_NAMES_FLAG, request);
        if (StringUtil.isNotBlank(traceAppNames)) {
            appName = traceAppNames + "," + appName;
        }
        MDC.put(TRACE_APP_NAMES_FLAG, appName);
        httpServletRequestWrapper.putHeader(TRACE_APP_NAMES_FLAG, appName);

        String hostName = ApplicationProvider.getHostName();
        String traceHostNames = WebUtil.getReqHeader(TRACE_HOST_NAMES_FLAG, request);
        if (StringUtil.isNotBlank(traceHostNames)) {
            hostName = traceHostNames + "," + hostName;
        }
        MDC.put(TRACE_HOST_NAMES_FLAG, hostName);
        httpServletRequestWrapper.putHeader(TRACE_HOST_NAMES_FLAG, hostName);

        String hostAddress = ApplicationProvider.getHostName();
        String traceHostAddress = WebUtil.getReqHeader(TRACE_HOST_ADDRESSES_FLAG, request);
        if (StringUtil.isNotBlank(traceHostAddress)) {
            hostAddress = traceHostAddress + "," + hostAddress;
        }
        MDC.put(TRACE_HOST_ADDRESSES_FLAG, hostAddress);
        httpServletRequestWrapper.putHeader(TRACE_HOST_ADDRESSES_FLAG, hostAddress);
        return httpServletRequestWrapper;
    }
}
