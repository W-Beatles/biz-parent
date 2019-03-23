package cn.waynechu.renting.core.constant;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019-03-23 17:37
 */
@UtilityClass
public class RedisPrefix {

    public static final String REDIS_COMMON_PREFIX = "renting.waynechu.cn:";

    public static final String REDIS_HOUSE_PREFIX = REDIS_COMMON_PREFIX + "house:";
}
