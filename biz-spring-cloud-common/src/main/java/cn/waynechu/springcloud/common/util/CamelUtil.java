package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhuwei
 * @since 2022/1/6 12:51 下午
 */
@UtilityClass
public class CamelUtil {

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");
    private static final Pattern CAMEL_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰
     */
    public static String lineToCamel(String str) {
        str = str.toLowerCase();
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String camelToLine(String str) {
        Matcher matcher = CAMEL_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
