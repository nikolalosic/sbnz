package sbnz.soft.nikola.service.util;

import java.util.*;

public class DateUtil {

    public static Date dateSomeDaysAgo(int numberOfDays) {

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -numberOfDays);

        return calendar.getTime();
    }

    public static Date dateSomeMonthsAgo(int numberOfMonths) {

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, -numberOfMonths);

        return calendar.getTime();
    }

    public static Date dateSomeYearsAgo(int numberOfYears) {

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.YEAR, -numberOfYears);

        return calendar.getTime();
    }

    public static Date today() {
        return new Date();
    }
}
