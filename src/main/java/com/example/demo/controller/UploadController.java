package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/excel/upload")
@Controller
public class UploadController {

    @GetMapping
    public String uploadForm() {
        return "upload-form";
    }

    @PostMapping
    public String saveFile(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            log.info("fileName : {}", fileName);
        }
        return "upload-form";
    }
}
