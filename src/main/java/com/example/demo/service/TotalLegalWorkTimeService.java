package com.example.demo.service;

import com.example.demo.domain.TotalLegalWorkTimeDto;
import com.example.demo.domain.WorkDetail;
import com.example.demo.repository.TotalLegalWorkTimeRepository;
import com.example.demo.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TotalLegalWorkTimeService {

    private final UploadRepository uploadRepository;

    private final TotalLegalWorkTimeRepository totalLegalWorkTimeRepository;

    @Transactional
    public void totalLegalWorkTimeSave() {

        List<WorkDetail> adminList = findAdminWorkList();
        List<TotalLegalWorkTimeDto> legalWorkTimeList = new ArrayList<>();

        for (int month : findMonthList(adminList)) {
            int totalWorkTime = 0;

            for (WorkDetail workDetail : adminList)
                if (workDetail.getDate().getMonthValue() == month && !workDetail.getHoliday() && !workDetail.getDayOfTheWeek().equals("토"))
                    totalWorkTime++;

            log.debug("totalWorkTime : {}", totalWorkTime);

            LocalDate localDate = LocalDate.of(adminList.get(0).getDate().getYear(), month, 1);
            legalWorkTimeList
                    .add(new TotalLegalWorkTimeDto(localDate,
                            quarterlyCalculation(month),
                            totalMonthTime(totalWorkTime),
                            legalMonthTime(localDate.lengthOfMonth())));
        }
        totalLegalWorkTimeRepository.save(legalWorkTimeList);
    }

    private List<WorkDetail> findAdminWorkList() {
        //admin 계정의 근무기록 가져오기
        List<WorkDetail> adminList =  uploadRepository.findAdmin();
        //새벽근무 확인을 위해서 다음 분기 첫날까지 근무기록이 있기 때문에 마지막 날은 빼준다. ex) 2분기(3월~6월) 시 7월 1일까지 근무기록이 있음
        adminList.remove(adminList.size() - 1);
        return adminList;
    }

    private List<Integer> findMonthList(List<WorkDetail> adminList) {
        //이번 분기 시작 월 구하기
        int firstMonth = adminList.get(0).getDate().getMonthValue();
        return List.of(firstMonth, firstMonth+1, firstMonth+2);
    }

    private int totalMonthTime(int totalWorkTime) {
        return totalWorkTime * 8 * 60 * 60;
    }

    private int legalMonthTime(Integer lengthOfMonth) {
        return (int) (((lengthOfMonth.doubleValue() * 40.0) / 7.0) * 60 * 60);
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
}
