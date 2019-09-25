package cn.waynechu.springcloud.apistarter.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
public class MDCProperty {
    public static final String MDC_CONFIG_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".mdc-filter";

    /**
     * 是否开启MDC。默认关闭
     */
    private boolean enable = false;
}
