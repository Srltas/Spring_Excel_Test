package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class WorkDetail {

    private String day;

    private String dayOfTheWeek;

    private String name;

    private String goOfficeTime;

    private String leaveOfficeTime;

    private String checkInTime;

    private String goalWorkingTime;

    private String workingTime;
}
