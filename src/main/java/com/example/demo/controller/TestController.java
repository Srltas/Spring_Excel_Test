package com.example.demo.controller;

import com.example.demo.service.HolidayService;
import com.example.demo.service.TotalLegalWorkTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/test")
@RestController
public class TestController {

    private final TotalLegalWorkTimeService totalLegalWorkTimeService;

    private final HolidayService holidayService;

    // TODO 결과 값 boxing해서 던져주는 객체 만들기
    @GetMapping("/legal")
    public void totalLegalWorkTime() {
        totalLegalWorkTimeService.totalLegalWorkTimeSave();
    }

    @GetMapping("/holiday")
    public void holiday() {
        holidayService.holidayCalc();
    }
}
