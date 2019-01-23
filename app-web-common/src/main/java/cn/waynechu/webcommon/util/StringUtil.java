package cn.waynechu.webcommon.util;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2018/11/6 16:21
 */
@UtilityClass
public class StringUtil {

    /**
     * Checks if a CharSequence is empty ("") or null.
     * <p>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * Checks if a CharSequence is empty (""), null or whitespace only.
     * <p>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }


    /**
     * @param value
     * @param tClass
     * @return
     */
    public static Object castToBaseObject(String value, Class<?> tClass) {
        if (isNotEmpty(value)) {
            tClass.cast(value);
        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    public static String capitalize(String name) {
        if (isNotEmpty(name)) {
            char[] cs = name.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return null;
    }
}
