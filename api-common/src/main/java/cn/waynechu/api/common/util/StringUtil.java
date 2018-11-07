package cn.waynechu.api.common.util;

/**
 * @author zhuwei
 * @date 2018/11/6 16:21
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isBlank(Object str) {
        return str == null || "".equals((str + "").trim());
    }

    /**
     * @param value
     * @param tClass
     * @return
     */
    public static Object castToBaseObject(String value, Class<?> tClass) {
        if (isNotNullOrEmpty(value)) {
            tClass.cast(value);
        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    public static String capitalize(String name) {
        if (isNotNullOrEmpty(name)) {
            char[] cs = name.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return null;
    }
}
