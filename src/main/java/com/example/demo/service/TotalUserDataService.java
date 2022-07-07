package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TotalUserDataService {

    private final HolidayRepository holidayRepository;

    private final TotalLegalWorkTimeRepository totalLegalWorkTimeRepository;

    private final UploadRepository uploadRepository;

    private final UserRepository userRepository;

    private final TotalUserDataRepository totalUserDataRepository;

    public void totalCalc() {
        List<TotalLegalWorkTime> totalLegalList = totalLegalWorkTimeRepository.findAll();
        List<User> userList = userRepository.findAll();

        int totalWorkTime = 0;
        int totalLegalTime = 0;

        for (TotalLegalWorkTime totalLegalWorkTime : totalLegalList) {
            totalWorkTime = totalWorkTime + totalLegalWorkTime.getTotalWorkTime();
            totalLegalTime = totalLegalTime + totalLegalWorkTime.getLegalWorkTime();
        }

        //
        List<TotalUserDataDto> totalUserDataDtos = new ArrayList<>();

        //각 유저마다 total 분기 근로 시간을 구함
        for (User user : userList) {
            List<WorkDetail> uploadList = uploadRepository.find(user.getName());
            List<HolidayCalc> holidayList = holidayRepository.find(user.getName());

            //마지막 날 하루 빼줘야 함
            uploadList.remove(uploadList.size() - 1);

            //분기 근로시간, 분기 휴가시간, 분기 야간 근로시간, 휴일 근로시간
            int myQuarterWork = 0;
            int myLeaveTime = 0;
            int myNightWork = 0;
            int myHolidayWork = 0;
            for (WorkDetail workDetail : uploadList) {
                myQuarterWork += workDetail.getTotalWork();
                myLeaveTime += workDetail.getLeave();
                myNightWork += workDetail.getNightWork();
                myHolidayWork += workDetail.getHolidayWork();
            }

            int myHoliday8HOver = 0;
            for (HolidayCalc holidayCalc : holidayList) {
                myHoliday8HOver += holidayCalc.getHoliday8hOver();
            }

            //소정근로 연장시간
            int myPrescribedOverWork = 0;
            if (myQuarterWork > totalWorkTime) {
                myPrescribedOverWork = myQuarterWork - totalLegalTime - totalWorkTime;
            }

            //법정근로 연장시간
            int myLegalOverWork = 0;
            if ((myQuarterWork - myLeaveTime) > totalLegalTime) {
                myLegalOverWork = myQuarterWork - myLeaveTime - totalLegalTime;
            }

            //분기 수당
            int myQuarterMoney = 0;
            //분기 수당 (분 제외) = 법정근로 연장시간(분 제외) * 1.5 + 야간 근로시간(분 포함) * 0.5 + 휴일 근로시간(분 포함) * 0.5 + 공휴일 8시간 초과(분 포함) * 0.5
            //계산 편의를 위해서 분단위로 계산 진행
            myQuarterMoney = (int)
                    (Math.floor((((myLegalOverWork / 3600)) * 60 * 1.5)) +
                            Math.floor((myNightWork / 60) * 0.5) +
                            Math.floor((myHolidayWork / 60) * 0.5) +
                            Math.floor((myHoliday8HOver / 60) + 0.5)) / 60;

            //보상 휴가 시간 + 분기 수당
            int myQuarterTotal = 0;
            myQuarterTotal = myPrescribedOverWork + myQuarterMoney;

            totalUserDataDtos.add(
                    TotalUserDataDto.builder()
                            .quarter(totalLegalList.get(1).getQuarter())
                            .name(user.getName())
                            .quarterWork(myQuarterWork)
                            .prescribedOverWork(myPrescribedOverWork)
                            .legalOverWork(myLegalOverWork)
                            .nightWork(myNightWork)
                            .holidayWork(myHolidayWork)
                            .holiday8HOver(myHoliday8HOver)
                            .leave(myLeaveTime)
                            .compensationLeave(myPrescribedOverWork)
                            .quarterMoney(myQuarterMoney)
                            .quarterTotal(myQuarterTotal)
                            .build()
            );
        }

        totalUserDataRepository.save(totalUserDataDtos);
    }
}
