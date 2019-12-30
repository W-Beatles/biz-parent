package cn.waynechu.bootstarter.logger.interceptor;

import cn.waynechu.springcloud.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * RestTemplate调用传递header信息
 *
 * @author zhuwei
 * @date 2019/12/30 19:03
 */
@Slf4j
public class RestTemplateTraceInterceptor extends BaseTraceInterceptor implements ClientHttpRequestInterceptor {

    public RestTemplateTraceInterceptor(List<String> needTraceHeaders) {
        super(needTraceHeaders);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Map<String, String> originHeaders = super.getHeaders();
        HttpHeaders headers = request.getHeaders();

        for (String traceHeaderKey : needTraceHeaders) {
            String traceHeaderValue = originHeaders.get(traceHeaderKey);
            // 兼容处理header key为全小写的情况
            if (StringUtil.isBlank(traceHeaderValue)) {
                traceHeaderValue = originHeaders.get(traceHeaderKey.toLowerCase());
            }

            if (StringUtil.isNotBlank(traceHeaderValue)) {
                headers.add(traceHeaderKey, traceHeaderValue);
            }
        }
        return execution.execute(request, body);
    }
}
