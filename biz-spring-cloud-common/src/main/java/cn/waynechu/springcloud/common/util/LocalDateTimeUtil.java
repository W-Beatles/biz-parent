package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2019/1/2 16:03
 */
@UtilityClass
public class LocalDateTimeUtil {

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

    /**
     * 按天数跨度拆分时间
     *
     * @param range       时间范围
     * @param intervalDay 拆分的天数跨度
     * @return 拆分之后的时间
     */
    public static List<LocalDateTimeRange> splitLocalDateTime(LocalDateTimeRange range, Integer intervalDay) {
        List<LocalDateTimeRange> returnValue = new ArrayList<>();

        LocalDateTime startTime = range.getStartTime();
        LocalDateTime endTime = range.getEndTime();

        LocalDateTime nextStartTime = startTime.plusDays(intervalDay);
        LocalDateTimeRange currentRange;
        while (endTime.isAfter(nextStartTime)) {
            currentRange = new LocalDateTimeRange();
            currentRange.setStartTime(startTime);
            currentRange.setEndTime(nextStartTime);

            returnValue.add(currentRange);

            // 下一批次开始时间调增1毫秒，防止重复计数
            startTime = nextStartTime.plusNanos(1);
            nextStartTime = nextStartTime.plusDays(intervalDay);
        }

        // 补齐时间范围
        LocalDateTimeRange polishRange = new LocalDateTimeRange();
        polishRange.setStartTime(startTime);
        polishRange.setEndTime(endTime);
        returnValue.add(polishRange);
        return returnValue;
    }

    public static void main(String[] args) {
        LocalDateTimeRange range = new LocalDateTimeRange();
        range.setStartTime(LocalDateTime.of(2018, 10, 1, 0, 0));
        range.setEndTime(LocalDateTime.of(2019, 10, 1, 0, 0));
        List<LocalDateTimeRange> localDateTimeRanges = splitLocalDateTime(range, 90);
        for (LocalDateTimeRange localDateTimeRange : localDateTimeRanges) {
            System.out.println(localDateTimeRange);
        }
    }
}
