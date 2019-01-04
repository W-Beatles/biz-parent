package cn.waynechu.app.boot.starter.common.properties;

import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/1/4 10:14
 */
@Data
public class RedisCacheProperties {

    /**
     * 是否开启Redis缓存
     */
    private boolean enable = false;

    /**
     * Redis存储前缀
     */
    private String keyPrefix;

    /**
     * 打印Redis操作详情
     */
    private boolean printOps = false;
}
