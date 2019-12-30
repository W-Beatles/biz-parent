package cn.waynechu.bootstarter.logger.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zhuwei
 * @date 2019/12/30 19:27
 */
@Slf4j
public abstract class BaseTraceInterceptor {

    /**
     * 需要传递的请求头名称列表
     */
    protected List<String> needTraceHeaders;

    public BaseTraceInterceptor(List<String> needTraceHeaders) {
        this.needTraceHeaders = needTraceHeaders;
    }

    /**
     * 获取当前线程请求头信息
     *
     * @return 请求头信息
     */
    protected Map<String, String> getHeaders() {
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
