package com.example.demo.controller;

import com.example.demo.repository.UploadRepository;
import com.example.demo.service.HolidayService;
import com.example.demo.service.TotalLegalWorkTimeService;
import com.example.demo.service.UploadService;
import com.example.demo.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/test")
@Controller
public class TestController {

    private final TotalLegalWorkTimeService totalLegalWorkTimeService;

    private final HolidayService holidayService;

    private final UploadRepository uploadRepository;

    private final ExcelUtil excelUtil;

    @GetMapping
    public String uploadTestView() {
        return "upload-test";
    }

    @PostMapping
    public String uploadTest(@RequestParam MultipartFile file) {
        uploadRepository.save(excelUtil.getListData(file, 4, 26));
        return "redirect:/test";
    }

    // TODO 결과 값 boxing해서 던져주는 객체 만들기
    @GetMapping("/legal")
    public String totalLegalWorkTime() {
        totalLegalWorkTimeService.totalLegalWorkTimeSave();
        return "redirect:/test";
    }

    @GetMapping("/holiday")
    public String holiday() {
        holidayService.holidayCalc();
        return "redirect:/test";
    }
}
