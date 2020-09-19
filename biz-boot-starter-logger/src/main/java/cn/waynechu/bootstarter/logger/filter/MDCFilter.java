package cn.waynechu.bootstarter.logger.filter;

import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.util.MDCUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.springcloud.common.util.UUIDUtil;
import cn.waynechu.springcloud.common.util.WebUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.*;

/**
 * MDC过滤器
 * <p>
 * 该过滤器用于添加请求信息到MDC上下文中，并且添加header追踪信息
 *
 * @author zhuwei
 * @since 2019/1/4 15:10
 */
@Slf4j
@Data
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // 添加请求头及MDC信息
            ModifyHttpServletRequestWrapper modifyHttpServletRequestWrapper = this.modifyRequestAndAddMdc((HttpServletRequest) servletRequest);

            // ResponseHeader返回requestId
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setHeader(HEADER_KEY_REQUEST_ID, modifyHttpServletRequestWrapper.getHeader(HEADER_KEY_REQUEST_ID));
            filterChain.doFilter(modifyHttpServletRequestWrapper, servletResponse);
        } finally {
            MDC.clear();
        }
    }

    private ModifyHttpServletRequestWrapper modifyRequestAndAddMdc(HttpServletRequest request) {
        ModifyHttpServletRequestWrapper httpServletRequestWrapper = new ModifyHttpServletRequestWrapper(request);

        MDCUtil.copyReqHeaderToMDC(httpServletRequestWrapper, HEADER_KEY_API_VERSION, HEADER_KEY_CHANNEL, HEADER_KEY_DEVICE_ID);

        String requestId = WebUtil.getReqHeader(HEADER_KEY_REQUEST_ID, request);
        if (StringUtil.isBlank(requestId)) {
            // 除了网关传递过来requestId外，应用本身也能产生requestId
            requestId = UUIDUtil.getShortUUID();
            httpServletRequestWrapper.putHeader(HEADER_KEY_REQUEST_ID, requestId);
        }
        MDC.put(MDC_KEY_REQUEST_ID, requestId);

        String scClientIp = WebUtil.getReqHeader(HEADER_KEY_SC_CLIENT_IP, request);
        MDC.put(MDC_KEY_SC_CLIENT_IP, scClientIp);
        httpServletRequestWrapper.putHeader(HEADER_KEY_SC_CLIENT_IP, scClientIp);

        String originUrl = WebUtil.getReqHeader(HEADER_KEY_ORIGIN_URL, request);
        MDC.put(MDC_KEY_ORIGIN_URL, originUrl);
        httpServletRequestWrapper.putHeader(HEADER_KEY_ORIGIN_URL, originUrl);

        String appId = ApplicationProvider.getAppId();
        String traceAppIds = WebUtil.getReqHeader(HEADER_KEY_TRACE_APP_IDS, request);
        if (StringUtil.isNotBlank(traceAppIds)) {
            appId = traceAppIds + "," + appId;
        }
        MDC.put(MDC_KEY_TRACE_APP_IDS, appId);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_APP_IDS, appId);

        String appName = ApplicationProvider.getAppName();
        String traceAppNames = WebUtil.getReqHeader(HEADER_KEY_TRACE_APP_NAMES, request);
        if (StringUtil.isNotBlank(traceAppNames)) {
            appName = traceAppNames + "," + appName;
        }
        MDC.put(MDC_KEY_TRACE_APP_NAMES, appName);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_APP_NAMES, appName);

        String hostName = ApplicationProvider.getHostName();
        String traceHostNames = WebUtil.getReqHeader(HEADER_KEY_TRACE_HOST_NAMES, request);
        if (StringUtil.isNotBlank(traceHostNames)) {
            hostName = traceHostNames + "," + hostName;
        }
        MDC.put(MDC_KEY_TRACE_HOST_NAMES, hostName);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_HOST_NAMES, hostName);

        String hostAddress = ApplicationProvider.getHostAddress();
        String traceHostAddress = WebUtil.getReqHeader(HEADER_KEY_TRACE_HOST_ADDRESSES, request);
        if (StringUtil.isNotBlank(traceHostAddress)) {
            hostAddress = traceHostAddress + "," + hostAddress;
        }
        MDC.put(MDC_KEY_TRACE_HOST_ADDRESSES, hostAddress);
        httpServletRequestWrapper.putHeader(HEADER_KEY_TRACE_HOST_ADDRESSES, hostAddress);

        return httpServletRequestWrapper;
    }
}
