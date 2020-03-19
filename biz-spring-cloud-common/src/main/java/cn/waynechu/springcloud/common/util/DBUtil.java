package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用于判断字段是否有效
 *
 * <pre>
 *     数据库规范: 所有字段不能设置为null，并要有默认值
 *     字符串类型: 默认值可以为''
 *     数值类型: 默认值可以为0
 *     时间类型: 默认值可以为'1900-01-01 00:00:00'
 * </pre>
 *
 * @author zhuwei
 * @date 2019/7/17 22:14
 */
@UtilityClass
public class DBUtil {

    /**
     * 数据库默认数值型填充值
     */
    public static final Integer INVALID_INTEGER = 0;

    /**
     * 数据库默认字符填充值
     */
    public static final String INVALID_STRING = "";

    /**
     * 数据库默认时间字段值
     */
    public static final String INVALID_DATE_STR = "1900-01-01 00:00:00";

    /**
     * 数据库默认时间字段值
     */
    public static final LocalDate INVALID_LOCAL_DATE = LocalDate.of(1900, 1, 1);

    /**
     * 数据库默认时间字段值
     */
    public static final LocalDateTime INVALID_LOCAL_DATE_TIME = LocalDateTime.of(1900, 1, 1, 0, 0);


    /**
     * 判断是否是有效的Integer类型
     *
     * @param num 数值
     * @return null 或 等于0 返回无效
     */
    public static boolean isValidInteger(Integer num) {
        return !Integer.valueOf(0).equals(num);
    }

    /**
     * 判断是否是有效的Long类型
     *
     * @param num 数值
     * @return null 或 等于0 返回无效
     */
    public static boolean isValidLong(Long num) {
        return !Long.valueOf(0).equals(num);
    }

    /**
     * 判断是否是有效的Date时间
     *
     * @param date 时间
     * @return true if 有效
     */
    public static boolean isValidDate(Date date) {
        if (date == null) {
            return false;
        }
        Date invalidDate;
        try {
            invalidDate = new SimpleDateFormat(DateUtil.FMT_DATE_TIME_DEFAULT).parse(INVALID_DATE_STR);
        } catch (ParseException e) {
            return false;
        }
        return date.after(invalidDate);
    }

    /**
     * 判断是否是有效的localDate时间
     *
     * @param localDate 时间
     * @return true if 有效
     */
    public static boolean isValidDate(LocalDate localDate) {
        if (localDate == null) {
            return false;
        }
        return !localDate.isEqual(LocalDate.of(1900, 1, 1));
    }

    /**
     * 判断是否是有效的localDateTime时间
     *
     * @param localDateTime 时间
     * @return true if 有效
     */
    public static boolean isValidDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }
        return !localDateTime.isEqual(LocalDateTime.of(1900, 1, 1, 0, 0, 0));
    }

    public static LocalDate toValidDate(LocalDate localDate) {
        return localDate.isEqual(LocalDate.of(1900, 1, 1)) ? null : localDate;
    }

    public static LocalDateTime toValidDateTime(LocalDateTime localDateTime) {
        return localDateTime.isEqual(LocalDateTime.of(1900, 1, 1, 0, 0, 0)) ? null : localDateTime;
    }

    public static LocalDate toValidDate(LocalDateTime localDateTime) {
        return localDateTime.isEqual(LocalDateTime.of(1900, 1, 1, 0, 0, 0)) ? null : localDateTime.toLocalDate();
    }
}
