package cn.waynechu.utility.common.consts;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/8/12 14:59
 */
@UtilityClass
public class RedisPrefix {

    private static final String REDIS_COMMON_PREFIX = "service-utility:";

    public interface Regions {
        String REGIONS_PREFIX = REDIS_COMMON_PREFIX + "regions:";

        String LIST_BY_PID_PREFIX = REGIONS_PREFIX + "listByPid:";
    }

    public interface ShortUrls {
        String INCREMENT_KEY = "increment-key";

        String STATISTICS_PREFIX = "statistic:";
    }

}
