package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter @Builder
public class HolidayCalcDto {

    private final long seq;

    private final LocalDate date;

    private final String name;

    private int holidayHoliday;

    private int holidayWeekday;

    private int weekdayHoliday;

    private int holiday8hOver;
}
