package com.example.demo.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtils {

    public static Date timestampOf(LocalDate time) {
        return time == null ? null : Date.valueOf(time);
    }

    public static LocalDateTime dateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
