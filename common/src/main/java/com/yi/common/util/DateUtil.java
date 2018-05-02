package com.yi.common.util;

/**
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cai on 2015/5/30 22:11.
 *
 * 常用的时间操作方法
 */
public class DateUtil {
    protected final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String YEAR_TO_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    public final static String DAY_FORMAT = "yyyy-MM-dd";

    public final static String MONTH_FORMAT = "yyyy-MM";

    public final static String TIME_FORMAT = "HH:mm";

    // 当前系统时间
    public static Date getCurrDate() {
        return new Date();
    }

    // 获取当前日期前一天
    public static Date getPreDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 得到前一天
        Date date = calendar.getTime();
        return date;
    }

    // 获取当前日期前n天,n为负数
    public static Date getPreDay(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, n); // 得到前一天
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取指定日期前n个月，n为负数
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getPreMonth(Date date, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, n);
        return c.getTime();
    }

    // 获取当前日期后一天
    public static Date getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); // 得到后一天
        Date date = calendar.getTime();
        return date;
    }

    // 日期转成字符串,指定日期格式
    public static String dateToStr(Date date, String format) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } else {
            return null;
        }
    }

    // 日期转成字符串,返回默认的yyyy-MM-dd HH:mm:ss格式
    public static String dateToStr(Date date) {
        if (date != null) {
            return dateToStr(date, DEFAULT_FORMAT);
        } else {
            return null;
        }
    }

    // 根据指定格式返回当前日期
    public static Date getCurrDate(String formatStr) {
        Date now = new Date();
        DateFormat sdf = new SimpleDateFormat(formatStr);
        now = strToDate(sdf.format(now), formatStr);
        return now;
    }

    // 字符串转换到时间格式
    public static Date strToDate(String dateStr, String formatStr) {
        if (null == dateStr || "".equals(dateStr))
            return null;
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("strToDate()失败", e);
        }
        return date;
    }

    /**
     * 返回当月第一天的日期
     */
    public static Date firstDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 返回当月最后一天的日期
     */
    public static Date lastDay(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 获取日期d的days天后的一个Date
     *
     * @param d
     * @param days
     * @return
     */
    public static Date getInternalDateByDay(Date d, int days) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    /**
     * 获取日期d的months个月后的一个集合
     *
     * @param
     * @return
     */
    public static List<String> getInternalDateByMonList(Date d, int months) {
        List<String> list = new ArrayList<String>();
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MONTH, months);
        //return now.getTime();
        Calendar c_begin = new GregorianCalendar();
        Calendar c_end = new GregorianCalendar();
        c_begin.setTime(d);
        c_end.setTime(d);
        c_end.add(Calendar.MONTH, months);
        while (c_begin.before(c_end)) {
            list.add(dateToStr(c_begin.getTime(), "yyyy-MM"));
            c_begin.add(Calendar.MONTH, 1);
        }
        return list;
    }

    /**
     * 获取日期d的years个年后的一个Date
     *
     * @param d
     * @return
     */
    public static Date getInternalDateByYear(Date d, int years) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    /**
     * 获取日期d的sec个秒后的一个Date
     *
     * @param d
     * @return
     */
    public static Date getInternalDateBySec(Date d, int sec) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    /**
     * 获取日期d的min个分钟后的一个Date
     *
     * @param d
     * @return
     */
    public static Date getInternalDateByMin(Date d, int min) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    /**
     * 获取日期d的hours个小时后的一个Date
     *
     * @param d
     * @param hours
     * @return
     */
    public static Date getInternalDateByHour(Date d, int hours) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    /**
     * @param
     * @return int
     * @throws
     * @Title: getCurrentMDays
     * @Description: 获取当前月天数
     */
    public static int getCurrentMDays(String years_month) throws ParseException {
        String yMmonth = years_month;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = new GregorianCalendar();
        Date date = sdf.parse(yMmonth);
        calendar.setTime(date);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;

    }

    // 获取date对应的星期,从0为周日开始计
    public static int getWeekly(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return day;
    }

    /**
     * 通过日期获取年龄
     *
     * @param birth
     * @return
     */
    public static String getBirthday(String birth) {
        String year = "";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            Date mydate;
            mydate = myFormatter.parse(birth);
            long day = ((date.getTime() - mydate.getTime()) / 1000) / (60 * 60 * 24);
            year = String.valueOf(Math.ceil((day / 365f)));
            year = year.substring(0, year.lastIndexOf("."));
        } catch (Exception e) {
            logger.error("getBirthday()失败:" + e);
        }
        return year;
    }

    /**
     * 在当前日期上加天数
     *
     * @param date
     * @return
     */
    public static Date addDate(int date) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, date);
        return c.getTime();
    }

    /**
     * 在指定日期上增加天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static Date addMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 与当前时间作比较
     *
     * @param dateStr 时间字符串
     * @param format  格式
     * @return -1 大于当前时间 0 等于 1小于
     */
    public static int matchDate(String dateStr, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        // 系统当前时间
        Calendar currentCalendar = Calendar.getInstance();
        // 比较时间
        Calendar specifyDateCalendar = Calendar.getInstance();
        int result = 0;
        try {
            specifyDateCalendar.setTime(sf.parse(dateStr));
            result = sf.parse(sf.format(currentCalendar.getTime())).compareTo(specifyDateCalendar.getTime());
        } catch (ParseException e) {
            logger.error("matchDate()失败", e);
        }
        // 比较
        return result;
    }

    /**
     * 对比时间大小
     * @param d1    时间1
     * @param d2    时间2
     * @param format    时间格式
     * @return -1: d1 < d2, 0: d1 = d2, 1: d1 > d2
     */
    public static int matchDate(String d1, String d2, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        int result = 0;
        try {
            date1.setTime(sf.parse(d1));
            date2.setTime(sf.parse(d2));
            result = date1.compareTo(date2);
        } catch (ParseException e) {
            logger.error("matchDate()失败", e);
        }

        return result;
    }
    public static int matchDate(Date d1, Date d2, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return matchDate(sf.format(d1), sf.format(d2), format);
    }
    public static int matchDate(Date d1, String d2, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return matchDate(sf.format(d1), d2, format);
    }
    public static int matchDate(String d1, Date d2, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return matchDate(d1, sf.format(d2), format);
    }

    /**
     * 判断当前日期是星期几<br> <br>
     *
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static int dayForWeek(String pTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            logger.error("dayForWeek操作失败", e);
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 获取当前日期的周一
     * @param date
     * @return
     */
    public static Date getMonday(Date date) {
        int i = getWeekly(date);
        if (i == 0) {
            return addDate(date, -6);
        } else {
            return addDate(date, 1 - i);
        }
    }
    /**
     * 获取当前日期的周日
     * @param date
     * @return
     */
    public static Date getSunday(Date date) {
        int i = getWeekly(date);
        if (i == 0) {
            return addDate(date, 1);
        } else {
            return addDate(date, 7 - i);
        }
    }

    /**
     * 获取两个日期之间的日期集合
     * @param start 格式"yyyy-MM-dd"
     * @param end 格式"yyyy-MM-dd"
     * @param divide 间隔天数
     * @return
     */
    public static List<String> getBetween2Date(String start, String end, int divide) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        try {
            Date date_start = sdf.parse(start);
            Date date_end = sdf.parse(end);
            Date date = date_start;
            Calendar cd = Calendar.getInstance();

            while (date.getTime() <= date_end.getTime()) {
                list.add(sdf.format(date));
                cd.setTime(date);
                cd.add(Calendar.DATE, divide);//增加一天
                date = cd.getTime();
            }
        } catch (ParseException e) {
            logger.error("getBetween2Date()失败", e);
        }

        return list;
    }

    /**
     * 获取两个日期之间的月份集合
     * @param start 格式"yyyy-MM-dd"
     * @param end 格式"yyyy-MM-dd"
     * @return
     */
    public static List<String> getBetween2Month(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        try {
            Date date_start = sdf.parse(start);
            Date date_end = sdf.parse(end);
            Date date = date_start;
            Calendar cd = Calendar.getInstance();

            while (date.getTime() <= date_end.getTime()) {
                list.add(sdf.format(date));
                cd.setTime(date);
                cd.add(Calendar.MONTH, 1);//增加一天
                date = cd.getTime();
            }
        } catch (ParseException e) {
            logger.error("getBetween2Date()失败", e);
        }

        return list;
    }

    /**
     * @param start,格式"yyyy-MM-dd"
     * @param end,格式"yyyy-MM-dd"
     * @return
     * @author lee 2013-05-28 获得两个日期的 天数集合
     */
    public static List<String> getBetween2Date(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        try {
            Date date_start = sdf.parse(start);
            Date date_end = sdf.parse(end);
            Date date = date_start;
            Calendar cd = Calendar.getInstance();

            while (date.getTime() <= date_end.getTime()) {
                list.add(sdf.format(date));
                cd.setTime(date);
                cd.add(Calendar.DATE, 1);//增加一天
                date = cd.getTime();
            }
        } catch (ParseException e) {
            logger.error("getBetween2Date()失败", e);
        }

        return list;
    }

    /**
     * @param str,格式"2016-01-01"
     * @param startformat,格式"yyyy-MM-dd"
     * @param endformat,格式"yyyyMMdd"
     * @return 20160101
     * @author movie 2017-09-25 日期格式转化
     */
    public static String formatDate(String str, String startformat, String endformat){
        SimpleDateFormat sf1 = new SimpleDateFormat(startformat);
        SimpleDateFormat sf2 =new SimpleDateFormat(endformat);
        String sfstr = "";
        try {
            sfstr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sfstr;
    }

    /**
     * @param start,格式"yyyy-MM-dd"
     * @param end,格式"yyyy-MM-dd"
     * @return
     * @author lee 2013-05-28 获得两个日期的 天数集合
     */
    public static List<String> getBetween2Date(String start, String end, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        List<String> list = new ArrayList<String>();
        try {
            Date date_start = sdf.parse(start);
            Date date_end = sdf.parse(end);
            Date date = date_start;
            Calendar cd = Calendar.getInstance();

            while (date.getTime() <= date_end.getTime()) {
                list.add(sdf.format(date));
                cd.setTime(date);
                cd.add(Calendar.DATE, 1);//增加一天
                date = cd.getTime();
            }
        } catch (ParseException e) {
            logger.error("getBetween2Date()失败", e);
        }

        return list;
    }

    /**
     * 获取当月日期集合。
     * @param month 2017-07
     * @return
     */
    public static List<String> getDaysByMonth(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startOfMonth = Calendar.getInstance();
        if (!StringUtils.isEmpty(month)) {
            startOfMonth.setTime(strToDate(month, "yyyy-MM"));
        }
        int idx = startOfMonth.get(Calendar.DAY_OF_MONTH);
        startOfMonth.add(Calendar.DATE, (1 - idx));

        Calendar endOfMonth = Calendar.getInstance();
        if (!StringUtils.isEmpty(month)) {
            endOfMonth.setTime(strToDate(month, "yyyy-MM"));
        }
        endOfMonth.add(Calendar.MONTH, 1);
        idx = endOfMonth.get(Calendar.DAY_OF_MONTH);
        endOfMonth.add(Calendar.DATE, (-idx));

        List<String> list = new ArrayList<String>();
        Date date_start = startOfMonth.getTime();
        Date date_end = endOfMonth.getTime();
        Date date = date_start;
        Calendar cd = Calendar.getInstance();
        while (date.getTime() <= date_end.getTime()) {
            list.add(sdf.format(date));
            cd.setTime(date);
            cd.add(Calendar.DATE, 1);//增加一天
            date = cd.getTime();
        }
        return list;
    }

    /**
     * 获取当季月份集合。
     *
     * @return
     */
    public static List<String> getMonthInCurrentQuarter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar today = Calendar.getInstance();
        int thisMonth = today.get(Calendar.MONTH);
        int startMonthOfQuarter;
        if (thisMonth >= 0 && thisMonth < 3) {
            startMonthOfQuarter = 0;
        } else if (thisMonth >= 3 && thisMonth < 6) {
            startMonthOfQuarter = 3;
        } else if (thisMonth >= 6 && thisMonth < 9) {
            startMonthOfQuarter = 6;
        } else {
            startMonthOfQuarter = 9;
        }
        Calendar startOfQurater = Calendar.getInstance();
        startOfQurater.set(Calendar.MONTH, startMonthOfQuarter);
        ArrayList<String> months = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            months.add(sdf.format(startOfQurater.getTime()));
            startOfQurater.add(Calendar.MONTH, 1);
        }
        return months;
    }

    /**
     * 获取当年月份集合。
     *
     * @return
     */
    public static List<String> getMonthsInCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MONTH, 0);
        ArrayList<String> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            months.add(sdf.format(today.getTime()));
            today.add(Calendar.MONTH, 1);
        }
        return months;
    }

    public static Date getStartOfTheDay(Date day) {
        if (day == null)
            throw new IllegalArgumentException("日期不能为空");

        String str = dateToStr(day, DAY_FORMAT) + " 00:00:00";
        return strToDate(str, DEFAULT_FORMAT);
    }

    public static Date getEndOfTheDay(Date day) {
        if (day == null)
            throw new IllegalArgumentException("日期不能为空");
        String str = dateToStr(day, DAY_FORMAT) + " 23:59:59";
        return strToDate(str, DEFAULT_FORMAT);
    }
    
    public static Date getStartOfTheMonth(Date day) {
        if (day == null)
            throw new IllegalArgumentException("日期不能为空");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String str = dateToStr(calendar.getTime(), DAY_FORMAT) + " 23:59:59";
        return strToDate(str, DEFAULT_FORMAT);
    }
    
    public static Date getEndOfTheMonth(Date day) {
        if (day == null)
            throw new IllegalArgumentException("日期不能为空");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        String str = dateToStr(calendar.getTime(), DAY_FORMAT) + " 23:59:59";
        return strToDate(str, DEFAULT_FORMAT);
    }

    public static Date getStartOfTheDay(String day) {
        return getStartOfTheDay(strToDate(day, DAY_FORMAT));
    }

    public static Date getEndOfTheDay(String day) {
        return getEndOfTheDay(strToDate(day, DAY_FORMAT));
    }

	public static int getWeekNumber(Date recordDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(recordDay);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
}
