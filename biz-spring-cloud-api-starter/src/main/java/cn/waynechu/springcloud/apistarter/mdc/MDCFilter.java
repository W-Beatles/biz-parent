package cn.waynechu.springcloud.apistarter.mdc;

import cn.waynechu.springcloud.common.util.MDCUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * MDC过滤器
 * <p>
 * 该过滤器用于添加信息到MDC上下文中
 * 如: 调用 MDC.put("traceNo", traceNoValue) 添加traceNo到MDC映射调试上下文中，这样就可以在log日志中追踪请求调用信息
 *
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
@Slf4j
public class MDCFilter implements Filter {
    public static final String TRACE_NO_FLAG = "traceNo";
    public static final String REQUEST_ID_FLAG = "requestId";

    public static final String API_VERSION_FLAG = "apiVersion";
    public static final String CHANNEL_FLAG = "channel";
    public static final String DEVICE_ID_FLAG = "deviceId";

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

    private void initMDC(HttpServletRequest request){
        MDCUtil.copyReqParamOrHeaderToMDC(request, TRACE_NO_FLAG);
        MDCUtil.copyReqHeaderToMDC(request, REQUEST_ID_FLAG, API_VERSION_FLAG, CHANNEL_FLAG, DEVICE_ID_FLAG);
    }
}
