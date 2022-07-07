package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
public class TotalUserDataDto {

    private String quarter;

    private String name;

    private int quarterWork;

    private int prescribedOverWork;

    private int legalOverWork;

    private int nightWork;

    private int holidayWork;

    private int holiday8HOver;

    private int leave;

    private int compensationLeave;

    private int quarterMoney;

    private int quarterTotal;
}
