package cn.waynechu.webcommon.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author zhuwei
 * @date 2018/11/5 16:18
 */
@UtilityClass
public class DateUtil {
    public static final String FMT_DATE_DEFAULT = "yyyy-MM-dd";
    public static final String FMT_DATE_SHOT = "yyyyMMdd";
    public static final String FMT_DATE_MONTH = "yyyy-MM";
    public static final String FMT_DATE_YEAR = "yyyy";

    public static final String FMT_DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_TIME_LONG = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String FMT_DATE_TIME_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FMT_DATE_TIME_HOUR = "yyyy-MM-dd HH";

    public static final String FMT_DATE_DEFAULT_CHINESE = "yyyy年MM月dd日";
    public static final String FMT_DATE_TIME_DEFAULT_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 解析成标准格式 yyyy-MM-dd HH:mm:ss
     * 时区为默认时区 TimeZone.getDefault()
     *
     * @param dateString 时间字符串
     * @return date
     */
    public static Date stringToDate(String dateString) {
        return stringToDate(dateString, FMT_DATE_TIME_DEFAULT);
    }

    /**
     * 按指定格式解析时间
     * 时区为默认时区 TimeZone.getDefault()
     *
     * @param dateString 时间字符串
     * @param dateFormat 时间格式
     * @return date
     */
    public static Date stringToDate(String dateString, String dateFormat) {
        return stringToDate(dateString, TimeZone.getDefault(), dateFormat);
    }

    /**
     * 按指定格式、指定时区解析时间
     *
     * @param dateString 时间字符串
     * @param timeZone   时区
     * @param dateFormat 时间格式
     * @return date
     */
    public static Date stringToDate(String dateString, TimeZone timeZone, String dateFormat) {
        checkDateFormatNotNull(dateFormat);
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }

        DateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(timeZone);

        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Parse dateString error");
        }
    }

    /**
     * 根据钟表时间获取，destDate天内的时间
     * 例如：8:00 => 2018-12-10 8:00:00
     *
     * @param date     目标日期
     * @param clockStr 钟表时间
     * @return date
     */
    public static Date clockStringToDate(Date date, String clockStr) {
        DateFormat dateFormat = new SimpleDateFormat(FMT_DATE_DEFAULT);
        String dateStr = dateFormat.format(date);

        try {
            return new SimpleDateFormat(DateUtil.FMT_DATE_TIME_MINUTE).parse(dateStr + " " + clockStr);
        } catch (ParseException e) {
            throw new RuntimeException("Parse date error");
        }
    }

    /**
     * 格式化成标准时间字符串
     *
     * @param date 时间
     * @return 标准时间字符串
     */
    public static String dateToString(Date date) {
        return dateToString(date, FMT_DATE_TIME_DEFAULT);
    }

    /**
     * 按指定格式格式化时间成字符串
     *
     * @param date       时间
     * @param dateFormat 时间格式
     * @return str
     */
    public static String dateToString(Date date, String dateFormat) {
        checkDateFormatNotNull(dateFormat);
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    /**
     * 按指定格式格式化时间成时间类型
     *
     * @param date       时间
     * @param dateFormat 时间格式
     * @return date
     */
    public static Date dateToDate(Date date, String dateFormat) {
        checkDateFormatNotNull(dateFormat);
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return stringToDate(formatter.format(date), dateFormat);
    }

    /**
     * 时间戳转化为时间类型
     *
     * @param timestamp 时间戳
     * @return date
     */
    public static Date timestampToDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    /**
     * 时间转化为时间戳
     *
     * @param date 时间
     * @return 时间戳
     */
    public static Timestamp dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 调整时间
     *
     * @param date         基础时间
     * @param calendarType 日历类型 如：Calendar.HOUR_OF_DAY
     * @param amount       调整大小
     * @return date
     */
    public static Date adjustDate(Date date, int calendarType, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarType, amount);
        return cal.getTime();
    }

    public static Date addYears(Date date, int amount) {
        return adjustDate(date, 1, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return adjustDate(date, 2, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return adjustDate(date, 3, amount);
    }

    public static Date addDays(Date date, int amount) {
        return adjustDate(date, 5, amount);
    }

    public static Date addHours(Date date, int amount) {
        return adjustDate(date, 11, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return adjustDate(date, 12, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return adjustDate(date, 13, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return adjustDate(date, 14, amount);
    }

    /**
     * 判断是否是今天
     *
     * @param srcDate 源时间
     * @return 是今天：true
     */
    public static boolean isToday(Date srcDate) {
        return isSameDay(new Date(), srcDate);
    }

    /**
     * 判断是否是相同的一天
     *
     * @param date1 源时间1
     * @param date2 源时间2
     * @return 相同：true
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * 判断是否是相同的一天
     *
     * @param cal1 源日历1
     * @param cal2 源日历2
     * @return 相同：true
     */

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                    && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } else {
            throw new IllegalArgumentException("The calendar must not be null");
        }
    }

    /**
     * 判断是否在现在时间之前
     *
     * @param srcDate 源时间
     * @return 早于：true
     */
    public static boolean isBeforeNow(Date srcDate) {
        if (srcDate == null) {
            return false;
        }
        return srcDate.compareTo(new Date()) < 0;
    }

    /**
     * 获取指定天的开始时间
     *
     * @param srcDate 源时间
     * @return 当天的开始时间
     */
    public static Date getDateStart(Date srcDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(srcDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定天的结束时间
     *
     * @param srcDate 源时间
     * @return 当天的结束时间
     */
    public static Date getDateEnd(Date srcDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(srcDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    // ****************************************
    // private start
    // ****************************************

    private static void checkDateFormatNotNull(String dateFormat) {
        if (dateFormat == null) {
            throw new IllegalArgumentException("The dateFormat parameter is null.");
        }
    }
}
