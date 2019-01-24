package cn.waynechu.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
public class MDCProperties {

    /**
     * 是否开启MDC。默认关闭
     */
    private boolean enable = false;

    /**
     * MDC前缀
     */
    private String prefix;
}
