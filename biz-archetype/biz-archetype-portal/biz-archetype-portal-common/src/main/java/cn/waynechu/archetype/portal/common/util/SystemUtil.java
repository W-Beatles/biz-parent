package cn.waynechu.archetype.portal.common.util;

/**
 * @author zhuwei
 * @date 2020-06-21 18:40
 */
public class SystemUtil {

    /**
     * 是否为Windows环境
     *
     * @return 是否为Windows环境
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * 是否为Linux环境
     *
     * @return 是否为Linux环境
     */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}
