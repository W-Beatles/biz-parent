package cn.waynechu.springcloud.apistarter.properties.nested;

import cn.waynechu.springcloud.apistarter.properties.CommonProperty;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2019/9/17 16:55
 */
@Data
public class DistributedLockProperty {
    public static final String DISTRIBUTED_LOCK_CONFIG_PREFIX = CommonProperty.COMMON_CONFIG_PREFIX + ".distributed-lock";

    /**
     * 是否开启分布式锁。默认关闭
     */
    private boolean enable = false;

    /**
     * 分布式锁前缀。默认为 distributed-lock
     */
    private String prefix = "distributed-lock";
}
