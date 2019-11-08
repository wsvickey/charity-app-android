package com.tech.agape4charity.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by JacH on 6/23/16.
 */
public class DateTimeHelper {

    public static String getDateFormatByType(DateTimeType type) {
        long unixSeconds = Helper.convertToUnixTimestamp();
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(getDateTimeType(type)); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    public static String getDateFormatByType(long unix, DateTimeType type) {
        long unixSeconds = unix;
        Date date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat(getDateTimeType(type)); // the format of your date
        sdf.setTimeZone(TimeZone.getDefault()); // give a timezone reference for formating (see comment at the bottom
        return sdf.format(date);
    }

    private static String getDateTimeType(DateTimeType type) {
        String dateType;

        switch (type) {
            case _FULL:
                dateType = "yyyy-MM-dd HH:mm:ss.SSS";
                break;
            case _12_HH_MM:
                dateType = "hh:mm a";
                break;
            case _MM_SS:
                dateType = "mm:ss";
                break;
            case _24_HH_MM:
                dateType = "HH:mm";
                break;
            case _DDD_MMM_dd:
                dateType = "EEE, MMM dd";
                break;
            case _DD_MM_YYYY:
                dateType = "dd/MM/yyyy";
                break;
            case _dd:
                dateType = "dd";
                break;
            case _DAY:
                dateType = "EEEE";
                break;
            default:
                dateType = "yyyy-MM-dd HH:mm:ss.SSS";
                break;
        }

        return dateType;
    }

    public enum DateTimeType {
        _12_HH_MM, _MM_SS, _24_HH_MM, _DDD_MMM_dd, _DD_MM_YYYY, _dd, _DAY, _FULL
    }

    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
