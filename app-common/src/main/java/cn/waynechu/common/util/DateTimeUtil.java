package cn.waynechu.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTimeUtil {
    public static final String FMT_DATE_TIME_LONG = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String FMT_DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_TIME_MINITE = "yyyy-MM-dd HH:mm";
    public static final String FMT_DATE_TIME_HOUR = "yyyy-MM-dd HH";

    public static final String FMT_DATE_DEFAULT = "yyyy-MM-dd";
    public static final String FMT_DATE_SHOT = "yyyyMMdd";
    public static final String FMT_DATE_MONTH = "yyyy-MM";
    public static final String FMT_DATE_YEAR = "yyyy";

    public static final String FMT_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    public static final String FMT_MONTH_DAY_HOUR = "MM月dd日HH点";
    public static final String FMT_MONTH_DAY = "MM月dd日";
    public static final String FMT_DATE_TIME_DEFAULT_ABBR = "yyyyMMddHHmmss";

    public static Date adjustDate(Date srcDate, int calendarType, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(srcDate);

        cal.add(calendarType, amount);

        return cal.getTime();
    }

    /**
     * parse the string into date with special timezone and format
     */
    public static Date parseStringToDate(String dateString, TimeZone timeZone, String dateFormat) {
        //the parameters checking
        if (dateFormat == null) {
            throw new IllegalArgumentException("The dateFormat parameter is null.");
        }

        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }

        //
        DateFormat dateFormatObj = new SimpleDateFormat(dateFormat);
        dateFormatObj.setTimeZone(timeZone);

        Date returnValue = null;
        try {
            returnValue = dateFormatObj.parse(dateString);
        } catch (ParseException e) {

        }

        //
        return returnValue;
    }

    public static Date parseStringToDate(String dateString) {
        return parseStringToDate(dateString, FMT_DATE_TIME_DEFAULT);
    }

    public static Date parseStringToDate(String dateString, String dateFormat) {
        return parseStringToDate(dateString, TimeZone.getDefault(), dateFormat);
    }

    /**
     * 根据钟表时间获取，dest天内的时间
     * 8:00->2017-12-10 8:00:00
     *
     * @param destDate
     * @param clockStr
     * @return
     */
    public static Date parseClockStringToDate(Date destDate, String clockStr) {
        DateFormat dateFormat = new SimpleDateFormat(FMT_DATE_DEFAULT);
        String dateStr = dateFormat.format(destDate);

        try {
            return new SimpleDateFormat(DateTimeUtil.FMT_DATE_TIME_MINITE).parse(dateStr + " " + clockStr);
        } catch (ParseException e) {
            throw new RuntimeException("时间转换异常", e);
        }
    }

    //format date to string.
    public static String formatDateToString(Date date, String dateFormat) {
        //the parameters checking
        if (dateFormat == null) {
            throw new IllegalArgumentException("The dateFormat parameter is null.");
        }

        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat dateFormatObj = new SimpleDateFormat(dateFormat);

        return dateFormatObj.format(date);
    }

    public static Date formatDateToDate(Date date, String dateFormat) {
        //the parameters checking
        if (dateFormat == null) {
            throw new IllegalArgumentException("The dateFormat parameter is null.");
        }

        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat dateFormatObj = new SimpleDateFormat(dateFormat);

        return parseStringToDate(dateFormatObj.format(date), dateFormat);
    }

    //calculate
    public static int diffOfDates(Date srcDate, Date destDate, int calendarType) {
        Calendar calSrc = Calendar.getInstance();
        calSrc.setTime(srcDate);

        Calendar calDest = Calendar.getInstance();
        calDest.setTime(destDate);

        return calSrc.get(calendarType) - calDest.get(calendarType);
    }

    //
    public static Date getDateDayValue(Date date) {
        return parseStringToDate(formatDateToString(date, FMT_DATE_DEFAULT), FMT_DATE_DEFAULT);
    }

    public static List<Date> getMonthsList(Date from, Date to) {
        Calendar fromCal = Calendar.getInstance();
        Calendar toCal = Calendar.getInstance();

        fromCal.setTime(from);
        fromCal.set(Calendar.DAY_OF_MONTH, 1);
        fromCal.set(Calendar.HOUR_OF_DAY, 0);
        fromCal.set(Calendar.MINUTE, 0);
        fromCal.set(Calendar.SECOND, 0);
        fromCal.set(Calendar.MILLISECOND, 0);

        toCal.setTime(to);
        toCal.set(Calendar.DAY_OF_MONTH, 1);
        toCal.set(Calendar.HOUR_OF_DAY, 0);
        toCal.set(Calendar.MINUTE, 0);
        toCal.set(Calendar.SECOND, 0);
        toCal.set(Calendar.MILLISECOND, 0);

        List<Date> returnList = new ArrayList<Date>();
        while (!toCal.before(fromCal)) {
            returnList.add(fromCal.getTime());

            fromCal.add(Calendar.MONTH, 1);
        }

        return returnList;
    }

    public static Date getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;

            c.add(Calendar.DATE, -weekday);

            SimpleDateFormat longSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat longSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            c.setTime(longSimpleDateFormat.parse(longSimpleDateFormat2.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c.getTime();
    }

    public static Date getWeekDayStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;

            c.add(Calendar.DATE, -weekday);

            SimpleDateFormat longSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat shortSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            c.setTime(longSimpleDateFormat.parse(shortSimpleDateFormat.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c.getTime();
    }

    public static Timestamp toTimestamp(Date date) {
        return toTimestamp(date, false);
    }

    public static Timestamp toTimestamp(Date date, boolean nullToNow) {
        return date == null ? (nullToNow ? new Timestamp(System.currentTimeMillis()) : null) : new Timestamp(date.getTime());
    }

    public static Date toDate(Timestamp timestamp) {
        return timestamp == null ? null : new Date(timestamp.getTime());
    }

    //
    public static Date getCurrentYearDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        try {
            int year = calendar.get(Calendar.YEAR);
            calendar.clear();
            calendar.set(Calendar.YEAR, year);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendar.getTime();
    }

    public static Date getDateStartDate(Date srcDate) {
        return DateTimeUtil.formatDateToDate(srcDate, DateTimeUtil.FMT_DATE_DEFAULT);
    }

    public static Date getDateEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getMonthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     * @param destDate
     * @param dateFormat
     * @return
     */
    private static boolean isThisTime(Date destDate, String dateFormat) {
        String nowStr = formatDateToString(new Date(), dateFormat);
        String destStr = formatDateToString(destDate, dateFormat);

        //
        if (destStr.equals(nowStr)) {
            return true;
        }
        return false;
    }

    /**
     * 判断选择的日期是否是今天
     *
     * @param destDate
     * @return
     */
    public static boolean isToday(Date destDate) {
        if (destDate != null) {
            return isThisTime(destDate, FMT_DATE_DEFAULT);
        }

        return false;
    }

    /**
     * 判断选择的日期是否是明天
     *
     * @param destDate
     * @return
     */
    public static boolean isTomorrow(Date destDate) {
        if (destDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(destDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);

            return isThisTime(calendar.getTime(), FMT_DATE_DEFAULT);
        }

        return false;
    }


}
