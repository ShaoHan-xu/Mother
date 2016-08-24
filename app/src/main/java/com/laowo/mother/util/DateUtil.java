package com.laowo.mother.util;

/**
 * 日期相关工具类
 * Created by xsh on 2016/8/12.
 */
public class DateUtil {

    /**
     * 获取所传时间与当前时间差
     *
     * @param timestamp 时间戳
     * @return xx分钟前 <br>
     * xx小时前 <br>
     * xx天前 <br>
     * xx月前
     */
    public static String calTimesBefore(long timestamp) {
        if (timestamp > 1000000000000L && timestamp < 1000000000000000L) {
            timestamp = timestamp / 1000;
        } else if (timestamp > 1000000000000000L && timestamp < 1000000000000000L) {
            timestamp = timestamp / 1000000;
        }

        int hour = (int) ((System.currentTimeMillis() / 1000 - timestamp) / 3600);
        int minute = (int) ((System.currentTimeMillis() / 1000 - timestamp) / 60);
        int day = hour / 24;
        int month = day / 30;
        int year = day / 365;

        if (year != 0) {
            return year + "年前";
        } else if (month != 0) {
            return month + "个月前";
        } else if (day != 0) {
            return day + "天前";
        } else if (hour != 0) {
            return hour + "小时前";
        } else {
            if (minute == 0)
                minute = 1;
            return minute + "分钟前";
        }
    }
}
