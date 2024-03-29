package com.example.demo.service;

import com.example.demo.domain.WorkDetailDto;
import com.example.demo.repository.UploadRepository;
import com.example.demo.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final ExcelUtil excelUtil;

    private final UploadRepository uploadRepository;

    private final TotalLegalWorkTimeService totalLegalWorkTimeService;

    private final HolidayService holidayService;

    private final TotalUserDataService totalUserDataService;

    @Transactional
    public void upload(MultipartFile file) {
        ArrayList<WorkDetailDto> workDetails = new ArrayList<>();

        List<WorkDetailDto> list = excelUtil.getListData(file, 4, 26);

        uploadRepository.save(list);

        totalLegalWorkTimeService.totalLegalWorkTimeSave();

        holidayService.holidayCalc();

        totalUserDataService.totalCalc();
    }
}
