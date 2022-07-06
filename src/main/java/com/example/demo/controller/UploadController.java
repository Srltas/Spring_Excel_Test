package com.example.demo.controller;

import com.example.demo.service.HolidayService;
import com.example.demo.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/excel/upload")
@Controller
public class UploadController {

    private final UploadService uploadService;
    private final HolidayService holidayService;

    @GetMapping
    public String uploadForm() {
        return "upload-form";
    }

    @GetMapping("/test")
    public String test() {
        holidayService.holidayCalc();
        return "redirect:/excel/upload";
    }

    @PostMapping
    public String saveFile(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            uploadService.upload(file);
        }
        return "redirect:/excel/upload";
    }

}
