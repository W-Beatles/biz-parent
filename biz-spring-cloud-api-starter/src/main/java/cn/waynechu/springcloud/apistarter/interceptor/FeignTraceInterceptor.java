package cn.waynechu.springcloud.apistarter.interceptor;

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
        for (String headerName : headers.keySet()) {
            if (feignTraceHeaders.contains(headerName)) {
                requestTemplate.header(headerName, getHeaders().get(headerName));
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
