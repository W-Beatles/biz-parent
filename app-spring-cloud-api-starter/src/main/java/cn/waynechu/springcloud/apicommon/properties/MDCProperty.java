package cn.waynechu.springcloud.apicommon.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
public class MDCProperty {
    public static final String MDC_CONFIG_PREFIX = CommonProperties.COMMON_CONFIG_PREFIX + ".mdc-filter";

    /**
     * 是否开启MDC。默认关闭
     */
    private boolean enable = false;

    /**
     * MDC前缀。默认为 ${spring.application.name}
     */
    private String prefix;
}
