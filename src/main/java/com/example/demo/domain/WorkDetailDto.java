package com.example.demo.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.math.NumberUtils.toInt;
import static org.apache.commons.lang3.math.NumberUtils.toLong;

@ToString
@Getter
public class WorkDetailDto {

    private static final long DEFAULT_MILLIS = 0;

    private LocalDate date;

    private String name;

    private int beginWork;

    private int endWork;

    private int totalWork;

    private int nightWork;

    private int holidayWork;

    private int leave;

    private String holidayCheck;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeginWork(String beginWork) {
        this.beginWork = changeMinutes(beginWork);
    }

    public void setEndWork(String endWork) {
        this.endWork = changeMinutes(endWork);
    }

    public void setTotalWork(String totalWork) {
        this.totalWork = changeMinutes(totalWork);
    }

    public void setNightWork(String nightWork) {
        this.nightWork = stringToInt(nightWork);
    }

    public void setHolidayWork(String holidayWork) {
        this.holidayWork = stringToInt(holidayWork);
    }

    public void setLeave(String leave) {
        this.leave = stringToInt(leave);
    }

    public void setHolidayCheck(String holidayCheck) {
        this.holidayCheck = holidayCheck;
    }

    public int changeMinutes(String time) {
        if (time.equals("-"))
            return 0;

        String[] timeArray = time.split(":");
        return (toInt(timeArray[0]) * 60) + toInt(timeArray[1]);
    }

    public int stringToInt(String millisString) {
        return Long.valueOf(millisToMinutes(toLong(millisString.split("/")[0], DEFAULT_MILLIS))).intValue();
    }

    private long millisToMinutes(long millis) {
        return (
            (TimeUnit.MILLISECONDS.toHours(millis) * 60) + TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes((TimeUnit.MILLISECONDS.toHours(millis)))
        );
    }

}
