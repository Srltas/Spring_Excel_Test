package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class WorkDetail {

    private final long seq;

    private final LocalDate date;

    private final String name;

    private final int beginWork;

    private final int endWork;

    private final int totalWork;

    private final int nightWork;

    private final int holidayWork;

    private final int leave;

    private final boolean holiday;
}
