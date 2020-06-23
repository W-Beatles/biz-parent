package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuwei
 * @date 2019/3/29 14:45
 */
@UtilityClass
public class WebUtil {

    /**
     * 从请求头或者请求参数中获取信息
     *
     * @param key     键
     * @param request HttpServletRequest
     * @return 值
     */
    public static String getReqParamOrHeader(String key, HttpServletRequest request) {
        // 1. parameter
        String parameter = request.getParameter(key);
        if (StringUtil.isEmpty(parameter)) {
            // 2. header
            parameter = request.getHeader(key);
        }
        return parameter;
    }

    public static String getReqParam(String key, HttpServletRequest request) {
        return request.getParameter(key);
    }

    public static String getReqHeader(String key, HttpServletRequest request) {
        return request.getHeader(key);
    }

    public static String getReqHeader(String key, ServerHttpRequest request) {
        return request.getHeaders().getFirst(key);
    }

    public static String getReqHeader(String key) {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return getReqHeader(key, requestAttributes.getRequest());
    }

    /**
     * 获取请求属性
     *
     * @return 请求属性
     */
    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
