package cn.waynechu.bootstarter.logger.interceptor;

import cn.waynechu.springcloud.common.util.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * feign调用传递header信息
 *
 * @author zhuwei
 * @date 2019/9/25 14:32
 */
@Slf4j
public class FeignTraceInterceptor implements RequestInterceptor {

    /**
     * 需要传递的请求头名称列表
     */
    @Setter
    private List<String> feignTraceHeaders;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String> headers = this.getHeaders();
        for (String traceHeaderKey : feignTraceHeaders) {
            String traceHeaderValue = headers.get(traceHeaderKey);
            // 兼容处理header key为全小写的情况
            if (StringUtil.isBlank(traceHeaderValue)) {
                traceHeaderValue = headers.get(traceHeaderKey.toLowerCase());
            }

            if (StringUtil.isNotBlank(traceHeaderValue)) {
                requestTemplate.header(traceHeaderKey, traceHeaderValue);
            }
        }
    }

    private Map<String, String> getHeaders() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("获取当前线程的RequestAttributes请求参数失败");
            return Collections.emptyMap();
        }

        HttpServletRequest request = requestAttributes.getRequest();
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
