package cn.waynechu.webcommon.util;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2018/11/6 16:21
 */
@UtilityClass
public class StringUtil {

    /**
     * 判断字符序列是否为 "" 或 null
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param cs 待检查的字符序列
     * @return {@code true} 字符序列是 "" 或 null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * See isEmpty(final CharSequence cs)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判断字符序列是否为 "" 或 null 或 空格
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs 待检查的字符序列
     * @return {@code true} 字符序列是 "" 或 null 或 空格
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

    /**
     * See isBlank(final CharSequence cs)
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 字符串首字母小写转化为大写
     *
     * @param str 首字母小写的字符串
     * @return 首字母大写的字符串
     */
    public static String capitalize(String str) {
        if (isNotEmpty(str)) {
            char[] cs = str.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);
        }
        return null;
    }
}
