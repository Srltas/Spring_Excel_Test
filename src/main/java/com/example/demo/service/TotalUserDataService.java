package com.example.demo.service;

import com.example.demo.domain.UserDto;
import com.example.demo.repository.HolidayRepository;
import com.example.demo.repository.TotalLegalWorkTimeRepository;
import com.example.demo.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TotalUserDataService {

    private final HolidayRepository holidayRepository;

    private final TotalLegalWorkTimeRepository totalLegalWorkTimeRepository;

    private final UploadRepository uploadRepository;

    public void finalCalculation() {
        List<UserDto> holidayList = holidayRepository.findAll();
//        totalLegalWorkTimeRepository.
    }
}
