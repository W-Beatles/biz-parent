package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuwei
 * @date 2019/3/29 14:45
 */
@UtilityClass
public class WebUtil {

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
}
