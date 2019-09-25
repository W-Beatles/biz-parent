package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhuwei
 * @date 2019/9/25 19:59
 */
@UtilityClass
public class MDCUtil {

    public static void copyReqParamToMDC(HttpServletRequest request, String... keys) {
        for (String key : keys) {
            String value = WebUtil.getReqParam(key, request);
            if (StringUtil.isNotBlank(value)) {
                MDC.put(key, value);
            }
        }
    }

    public static void copyReqHeaderToMDC(HttpServletRequest request, String... keys) {
        for (String key : keys) {
            String value = WebUtil.getReqHeader(key, request);
            if (StringUtil.isNotBlank(value)) {
                MDC.put(key, value);
            }
        }
    }

    public static void copyReqParamOrHeaderToMDC(HttpServletRequest request, String... keys) {
        for (String key : keys) {
            String value = WebUtil.getReqParamOrHeader(key, request);
            if (StringUtil.isNotBlank(value)) {
                MDC.put(key, value);
            }
        }
    }
}
