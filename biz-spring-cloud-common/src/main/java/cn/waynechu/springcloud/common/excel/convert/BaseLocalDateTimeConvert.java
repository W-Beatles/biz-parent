package cn.waynechu.springcloud.common.excel.convert;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;

/**
 * @author zhuwei
 * @since 2020/11/24 22:22
 */
public abstract class BaseLocalDateTimeConvert {

    /**
     * 获取 DateTimeFormatter
     *
     * @param field          属性
     * @param defaultPattern 默认pattern
     * @return DateTimeFormatter
     */
    protected static DateTimeFormatter getDateTimeFormatter(Field field, String defaultPattern) {
        JsonFormat format = field.getAnnotation(JsonFormat.class);
        return DateTimeFormatter.ofPattern(format != null ? format.pattern() : defaultPattern);
    }
}
