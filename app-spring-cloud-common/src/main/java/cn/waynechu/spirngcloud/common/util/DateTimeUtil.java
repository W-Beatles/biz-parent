package cn.waynechu.spirngcloud.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/1/2 16:03
 */
@UtilityClass
public class DateTimeUtil {

    public static LocalDateTime toLocalDateTimeFromDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date toDateFromLocalDateTime(LocalDateTime date) {
        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate toLocalDateFromDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDateFromLocalDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalTime toLocalTimeFromDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
    }

    public static Date toDateFromLocalTime(LocalTime date) {
        Instant instant = date.atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate toLocalDateFromString(String text, String format) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate toLocalDateFromString(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        } else if (StringUtils.containsAny(text, "年月日")) {
            return toLocalDateFromString(text, "uuuu年MM月dd日");
        } else if (StringUtils.contains(text, "-")) {
            return toLocalDateFromString(text, "uuuu-MM-dd");
        }
        return LocalDate.parse(text);
    }

    public static String toStringFromLocalDate(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String toStringFromLocalDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return toStringFromLocalDate(date, "uuuu-MM-dd");
    }

    public static LocalDateTime toLocalDateTimeFromString(String text, String format) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime toLocalDateTimeFromString(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        } else if (StringUtils.contains(text, "-")) {
            return toLocalDateTimeFromString(text, "uuuu-MM-dd HH:mm:ss");
        }
        return LocalDateTime.parse(text);
    }

    public static String toStringFromLocalDateTime(LocalDateTime date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static String toStringFromLocalDateTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return toStringFromLocalDateTime(date, "uuuu-MM-dd HH:mm:ss");
    }
}
