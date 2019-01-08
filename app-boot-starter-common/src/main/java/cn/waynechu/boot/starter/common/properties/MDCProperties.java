package cn.waynechu.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
public class MDCProperties {

    /**
     * 是否开启MDC
     */
    private boolean enable;

    /**
     * MDC前缀
     */
    private String prefix;
}
