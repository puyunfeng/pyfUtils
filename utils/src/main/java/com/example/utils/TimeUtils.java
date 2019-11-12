package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    private static class TimeUtilsHolder {
        private static final TimeUtils INSTANCE = new TimeUtils();
    }

    private TimeUtils() {

    }

    public static final TimeUtils getInstance() {
        return TimeUtilsHolder.INSTANCE;
    }

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取指定格式的时间戳
     *
     * @param mDateStr    时间 2019-11-11 12:12
     * @param mTimeFormat 时间格式 yyyy-MM-dd HH:mm
     * @return
     */
    public long getTime(String mDateStr, String mTimeFormat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(mTimeFormat).parse(mDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取当前时间的时间戳
     */
    public long getTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取指定格式的时间
     *
     * @param mDate       data对象
     * @param mTimeFormat 时间格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getData(Date mDate, String mTimeFormat) {
        String date = new SimpleDateFormat(mTimeFormat).format(mDate);
        return date;
    }

    /**
     * 获取当前指定格式的时间
     *
     * @param mTimeFormat 时间格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getData(String mTimeFormat) {
        return getData(new Date(), mTimeFormat);
    }

    /**
     * 时间戳转换成指定格式的数据
     *
     * @param timestamp   时间戳
     * @param mTimeFormat 指定格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getData(long timestamp, String mTimeFormat) {
        return getData(time2Date(timestamp), mTimeFormat);
    }

    /**
     * 时间戳转换成Date
     *
     * @param timestamp
     * @return
     */
    public Date time2Date(long timestamp) {
        return new Date(timestamp * 1000);
    }

    /**
     * 获取今天00：00时间的方法
     * 获取一天零界点的时间（上边界）
     *
     * @param mTimeFormat 指定格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getCalendarBoundT(String mTimeFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return getData(calendar.getTimeInMillis() / 1000, mTimeFormat);
    }

    /**
     * 获取今天23:59时间的方法
     * 获取一天零界点的时间（下边界）
     *
     * @param mTimeFormat 指定格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getCalendarBoundB(String mTimeFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long mNextTime = calendar.getTimeInMillis() / 1000 + (24 * 60 * 60 - 1);
        return getData(mNextTime, mTimeFormat);
    }

}
