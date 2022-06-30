package com.example.demo.service;

import com.example.demo.domain.TotalLegalWorkTimeDto;
import com.example.demo.domain.WorkDetailDto;
import com.example.demo.repository.UploadRepository;
import com.example.demo.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final ExcelUtil excelUtil;

    private final UploadRepository uploadRepository;

    private final HolidayService holidayService;

    @Transactional
    public void upload(MultipartFile file) {
        ArrayList<WorkDetailDto> workDetails = new ArrayList<>();

        List<WorkDetailDto> list = excelUtil.getListData(file, 4, 26);

        log.debug("list values : {}", list);

        uploadRepository.save(list);

        List<TotalLegalWorkTimeDto> totalLegalWorkTimeDtos = totalLegalWorkTimeSave(list);

        log.debug("totalLegalWorkTimeDtos : {}", totalLegalWorkTimeDtos);

        uploadRepository.TotalLegalWorkTimeSave(totalLegalWorkTimeDtos);

        holidayService.holidayCalc();
    }

    private List<TotalLegalWorkTimeDto> totalLegalWorkTimeSave(List<WorkDetailDto> list) {

        //admin만 따로 list 만들기
        List<WorkDetailDto> adminList = getAdminList(list);

        //첫 번째 시작 월 구해오기
        int firstMonth = adminList.get(0).getDate().getMonthValue();
        int[] monthArray = {firstMonth, firstMonth+1, firstMonth+2};

        List<TotalLegalWorkTimeDto> legalWorkTimeList = new ArrayList<>();

        for (int month : monthArray) {
            int totalWorkTime = 0;

            for (WorkDetailDto workDetailDto : adminList) {
                if (workDetailDto.getDate().getMonthValue() == month && !workDetailDto.isHoliday()) {
                    totalWorkTime++;
                }
            }
            LocalDate localDate = LocalDate.of(adminList.get(0).getDate().getYear(), month, 1);

            legalWorkTimeList
                    .add(new TotalLegalWorkTimeDto(localDate, quarterlyCalculation(month), totalMonthTime(totalWorkTime), legalMonthTime(localDate.lengthOfMonth())));
        }
        return legalWorkTimeList;
    }

    private int totalMonthTime(int totalWorkTime) {
        return totalWorkTime * 8 * 60;
    }

    private int legalMonthTime(int lengthOfMonth) {
        return ((lengthOfMonth * 40) / 7) * 60;
    }

    private String quarterlyCalculation(int firstMonth) {
        switch (firstMonth) {
            case 1: case 2: case 3:
                return "1";
            case 4: case 5: case 6:
                return "2";
            case 7: case 8: case 9:
                return "3";
            default:
                return "4";
        }
    }

    private List<WorkDetailDto> getAdminList(List<WorkDetailDto> list) {
        List<WorkDetailDto> adminList = new ArrayList<WorkDetailDto>();
        for (WorkDetailDto workDetailDto : list) {
            if (workDetailDto.getName().equals("admin")) {
                adminList.add(workDetailDto);
            }
        }
        adminList.remove(adminList.size() - 1);
        return adminList;
    }
}
