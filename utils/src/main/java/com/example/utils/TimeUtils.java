package com.example.utils;

import java.text.SimpleDateFormat;
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
     * @param mDate data对象
     * @param mTimeFormat  时间格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getData(Date mDate, String mTimeFormat) {
        String date = new SimpleDateFormat(mTimeFormat).format(mDate);
        return date;
    }
    /**
     * 获取当前指定格式的时间
     * @param mTimeFormat  时间格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public String getData(String mTimeFormat) {
        return getData(new Date(),mTimeFormat);
    }

}
