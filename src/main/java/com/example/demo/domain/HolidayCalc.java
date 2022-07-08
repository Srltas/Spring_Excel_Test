package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class HolidayCalc {

    private final long seq;

    private final LocalDate date;

    private final String name;

    private final int holidayHoliday;

    private final int holidayWeekday;

    private final int weekdayHoliday;

    private final int holiday8hOver;
}
