package com.ifenduo.mahattan_x.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ll on 2018/3/13.
 */

public class DateTool {
    public final static String PATTERN_HH_MM = "HH:mm";
    public final static String PATTERN_MM_DD = "MM-dd";
    public final static String PATTERN_YYYY_MM = "yyyy-MM";
    public final static String PATTERN_YYYY = "yyyy";

    public final static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String PATTERN_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public final static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    private static Calendar msgCalendar = null;

    public static String getYYYYMMDDHHMM(long mills) {
        if (msgCalendar == null) {
            msgCalendar = Calendar.getInstance();
        }
        mills *= 1000;
        msgCalendar.setTimeZone(TimeZone.getDefault());
        msgCalendar.setTime(new Date(mills));
        return new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM).format(msgCalendar.getTime());
    }



    public static String formatTimeWithPattern(long mills, String pattern) {
        if (msgCalendar == null) {
            msgCalendar = Calendar.getInstance();
        }
        msgCalendar.setTimeZone(TimeZone.getDefault());
        msgCalendar.setTime(new Date(mills));
        return new SimpleDateFormat(pattern).format(new Date(mills));
    }

//    public static String getYYYYMMDD(long mills) {
//        if (msgCalendar == null) {
//            msgCalendar = Calendar.getInstance();
//        }
//        mills *= 1000;
//        msgCalendar.setTimeZone(TimeZone.getDefault());
//        msgCalendar.setTime(new Date(mills));
//        return new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SS).format(msgCalendar.getTime());
//    }

    public static int getAge(long birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        Calendar birth= Calendar.getInstance();
        birth.setTimeInMillis(birthDay);
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        int yearBirth = birth.get(Calendar.YEAR);
        int monthBirth = birth.get(Calendar.MONTH);
        int dayOfMonthBirth = birth.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }
}
