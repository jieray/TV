package com.fongmi.android.tv.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {
    public static String pattern = "yyyy-MM-dd";
    public static String pattern2="MM月dd日";
    public static String pattern3="yyyy/MM/dd";
    public static String pattern4="HHmm";
    public static String pattern5="EEEE";
    public static SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    public static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatter3 = new SimpleDateFormat(pattern2);
    public static SimpleDateFormat formatter4 = new SimpleDateFormat(pattern3);
    public static SimpleDateFormat formatter5 = new SimpleDateFormat(pattern4);
    public static SimpleDateFormat formatter6 = new SimpleDateFormat(pattern5);
    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        String dateString = formatter.format(new Date());
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyyMMddHHmmss
     */
    public static String getStringAllDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 MM月dd日
     */
    public static String getStringDateShort2() {
        String dateString = formatter3.format(new Date());
        return dateString;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式 MM月dd日
     */
    public static String getStringDateShort5() {
        String dateString = formatter5.format(new Date());
        return dateString;
    }

    public static String getStringDateShort6() {
        String dateString = formatter6.format(new Date());
        return dateString;
    }

    public static String getStringDateShort7() {
        String dateString = "";
        String dateString1 = formatter5.format(new Date());
        String dateString2 = formatter6.format(new Date());
        dateString = dateString2 + ", " + dateString1;
        return dateString;
    }



}
