package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class HolidayCalcDto {

    private final LocalDate date;

    private final String name;

    private int holidayHoliday;

    private int holidayWeekday;

    private int weekdayHoliday;

    private int holiday8hOver;
}
