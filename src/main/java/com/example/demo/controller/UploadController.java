package com.example.demo.controller;

import com.example.demo.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
            uploadService.upload(file);
        }
        return "redirect:/excel/upload";
    }

}
