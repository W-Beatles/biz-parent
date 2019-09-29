package cn.waynechu.springcloud.apistarter.properties;

import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuwei
 * @date 2019/9/29 15:04
 */
@Data
public class FeignTraceProperty {

    public static final String FEIGN_TRACE_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".feign-trace-interceptor";

    /**
     * 是否开启feign请求头拦截器。默认关闭
     */
    private boolean enable = false;

    /**
     * feign请求头拦截器配置，用于feign调用之间传递的header信息。不区分大小写，默认为空
     */
    private String feignTraceHeaders = "";

    public List<String> getFeignTraceHeaders() {
        if (feignTraceHeaders.isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(feignTraceHeaders.split(","))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
