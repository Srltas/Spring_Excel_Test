package com.example.demo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class TotalLegalWorkTimeDto {

    private final LocalDate date;

    private final String quarter;

    // TODO double 변경
    private final int totalWorkTime;

    // TODO double 변경
    private final int legalWorkTime;


}
