package cn.waynechu.bootstarter.sequence.util;

import lombok.Data;
import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @since 2020/6/11 20:15
 */
@Data
@UtilityClass
public class TagPathUtil {

    public static String getTagPath(String bizTag) {
        return String.format("/%s", bizTag);
    }

    public static String getWorkerPath(String bizTag, Integer workerId) {
        return String.format("/%s/%s", bizTag, workerId);
    }
}
