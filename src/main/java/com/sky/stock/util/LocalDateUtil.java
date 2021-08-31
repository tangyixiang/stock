package com.sky.stock.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocalDateUtil {

    public static String getAmericanDate() {
        TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
        Date date = Calendar.getInstance(timeZone, Locale.US).getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static long getAmericanDateTime(){
        TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
        Date date = Calendar.getInstance(timeZone, Locale.US).getTime();
        return date.getTime();
    }
}
