package cn.waynechu.app.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
@Data
public class MDCProperties {

    /**
     * 开启MDC
     */
    private boolean enable;

    /**
     * MDC前缀
     */
    private String prefix;
}
