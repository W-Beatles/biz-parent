package cn.waynechu.springcloud.apistarter.interceptor;

import cn.waynechu.springcloud.common.util.CollectionUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Feign调用传递header信息
 *
 * @author zhuwei
 * @since 2019/9/25 14:32
 */
@Slf4j
public class FeignTraceInterceptor extends BaseTraceInterceptor implements RequestInterceptor {

    public FeignTraceInterceptor(List<String> needTraceHeaders) {
        super(needTraceHeaders);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpHeaders headers = super.getHeaders();
        for (String traceHeaderKey : needTraceHeaders) {
            List<String> traceHeaderValue = headers.get(traceHeaderKey);
            if (CollectionUtil.isNotNullOrEmpty(traceHeaderValue)) {
                requestTemplate.header(traceHeaderKey, traceHeaderValue);
            }
        }
    }
}
