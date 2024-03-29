package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@Getter
public class TotalLegalWorkTime {

    private final long seq;

    private final LocalDate date;

    private final String quarter;

    private final int totalWorkTime;

    private final int legalWorkTime;
}
