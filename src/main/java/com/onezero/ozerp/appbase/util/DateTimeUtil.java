package com.onezero.ozerp.appbase.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    public static final String TIME_ZONE = "Etc/UTC";

    public static long convertToSystemDefaultTimeZone(LocalDate date) {
        ZoneId zoneId = TimeZone.getTimeZone(TIME_ZONE).toZoneId(); // or: ZoneId.of("Europe/Oslo");
        return date.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }


    public static long convertToSystemDefaultDateTimeZone(LocalDateTime date) {
        ZonedDateTime zdt = ZonedDateTime.of(date, TimeZone.getTimeZone(TIME_ZONE).toZoneId());
        return zdt.toInstant().toEpochMilli();
    }

    public static Timestamp getTimestamp(Long timestamp) {
        if (timestamp != null) {
            Date date = new Date(timestamp);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
            String formatted = format.format(date);
            Timestamp timeStamp = Timestamp.valueOf(formatted);
            return timeStamp;
        } else {
            return null;
        }
    }

    public static boolean isValidStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        return endDate.compareTo(startDate) >= 0;
    }
}