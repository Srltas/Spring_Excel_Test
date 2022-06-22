package com.example.demo.controller;

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

    @GetMapping
    public String uploadForm() {
        return "upload-form";
    }

    @PostMapping
    public String saveFile(@RequestParam MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            log.info("fileName : {}", fileName);

            uploadService.upload(file);
        }
        return "upload-form";
    }
}
