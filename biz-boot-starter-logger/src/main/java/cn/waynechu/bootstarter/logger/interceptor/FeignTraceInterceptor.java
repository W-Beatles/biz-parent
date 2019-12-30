package cn.waynechu.bootstarter.logger.interceptor;

import cn.waynechu.springcloud.common.util.StringUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Feign调用传递header信息
 *
 * @author zhuwei
 * @date 2019/9/25 14:32
 */
@Slf4j
public class FeignTraceInterceptor extends BaseTraceInterceptor implements RequestInterceptor {

    public FeignTraceInterceptor(List<String> needTraceHeaders) {
        super(needTraceHeaders);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String> headers = super.getHeaders();
        for (String traceHeaderKey : needTraceHeaders) {
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
}
