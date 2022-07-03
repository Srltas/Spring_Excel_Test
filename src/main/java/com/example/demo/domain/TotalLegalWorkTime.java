package com.example.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class TotalLegalWorkTime {

    private final long seq;

    private final LocalDate date;

    private final String quarter;

    private final int totalWorkTime;

    // TODO 법정근로시간을 쓸 때 소수점은 어떻게 처리하는지?
    private final int legalWorkTime;
}
