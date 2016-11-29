package prometheus.zero.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ishwor on 02/11/16.
 */

public class DateTimeUtil {
    private static Calendar calendar;

    public static HashMap<String, Integer> getDate(int dayOffSet) {
        HashMap<String, Integer> dateMap = new HashMap<>();
        calendar = Calendar.getInstance();
        if (dayOffSet > 0) {
            calendar.add(Calendar.DAY_OF_MONTH, dayOffSet);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateMap.put("year", year);
        dateMap.put("month", month + 1);
        dateMap.put("day", day);

        return dateMap;
    }

    public static String getDayOfTheWeek(String date) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String goal = "";
        try {
            Date date2 = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            goal = outFormat.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return goal;
    }

    public static String getCurrent24HourTime(int additionalMinute) {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:45"));
        if (additionalMinute > 0) {
            calendar.add(Calendar.MINUTE, additionalMinute);
        }
        Date currentTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:45"));
        /*-------------------------------------------------------*/
        String time = date.format(currentTime);

        String[] a = time.split(":");
        //hour = Integer.parseInt(a[0]);
        String[] b = a[1].split("\\s+");
        int minute = Integer.parseInt(b[0]);

        return time;
    }

    public static String getCurrent12HourTime(int additionalMinute) {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:45"));
        if (additionalMinute > 0) {
            calendar.add(Calendar.MINUTE, additionalMinute);
        }
        Date currentTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("KK:mm a", Locale.ENGLISH);
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:45"));
        /*-------------------------------------------------------*/
        String time = date.format(currentTime);

        String[] a = time.split(":");
        //hour = Integer.parseInt(a[0]);
        String[] b = a[1].split("\\s+");
        int minute = Integer.parseInt(b[0]);

        return time;
    }

    public static String convert24to12(String time24Hour) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        try {
            Date date = parseFormat.parse(time24Hour);
            LogUtil.log("24 to 12", displayFormat.format(date));
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convert12to24(String time12Hour) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date date = parseFormat.parse(time12Hour);
            LogUtil.log("12 to 24", displayFormat.format(date));
            return displayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDayOfMonthSuffix(int n) {
        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st"};
        return suffixes[n];

    }

    public static HashMap<String, Object> offSetDay(String fullDate, int offSet) {
        String date[] = TextUtils.split(fullDate, "-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

        /*int newOffSet;
        if (offSet > 0) {
            newOffSet = offSet - 1;
        } else {
            newOffSet = offSet;
        }*/

        calendar.add(Calendar.DAY_OF_MONTH, offSet);

        int year, month, day;
        String newFullDate;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        newFullDate = year + "-" + month + "-" + day;

        HashMap<String, Object> dateMap = new HashMap<>();
        dateMap.put("year", year);
        dateMap.put("month", month);
        dateMap.put("day", day);
        dateMap.put("dayOfWeek", getDayOfTheWeek(newFullDate));
        dateMap.put("fullDate", newFullDate);
        return dateMap;
    }
}
