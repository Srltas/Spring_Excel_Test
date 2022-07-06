package com.example.demo.service;

import com.example.demo.domain.HolidayCalcDto;
import com.example.demo.domain.UserDto;
import com.example.demo.domain.WorkDetail;
import com.example.demo.repository.HolidayRepository;
import com.example.demo.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    private final UploadRepository uploadRepository;

    public void holidayCalc() {
        List<UserDto> userList = holidayRepository.findAll();

        boolean holidayCheck = false;
        boolean nextHolidayCheck = false;

        List<HolidayCalcDto> holidayCalcDtos = new ArrayList<>();

        for (UserDto userDto : userList) {
            List<WorkDetail> workDetails = uploadRepository.find(userDto.getName());

            for (int i = 0; i < workDetails.size(); i++) {
                WorkDetail workDetail = workDetails.get(i);
                holidayCheck = workDetail.isHoliday();

                if (i < workDetails.size() - 1) {
                    nextHolidayCheck = workDetails.get(i + 1).isHoliday();
                } else {
                    nextHolidayCheck = false;
                }

                if (holidayCheck && nextHolidayCheck) {
                    holidayCalcDtos.add(holidayHoliday(workDetail));
                } else if (holidayCheck && !nextHolidayCheck) {
                    holidayCalcDtos.add(holidayWeekday(workDetail));
                } else if (!holidayCheck && nextHolidayCheck) {
                    holidayCalcDtos.add(weekdayHoliday(workDetail));
                } else {
                    holidayCalcDtos.add(weekdayWeekday(workDetail));
                }
            }
        }
        holidayRepository.save(holidayCalcDtos);
    }

    private HolidayCalcDto weekdayWeekday(WorkDetail workDetail) {
        return HolidayCalcDto.builder()
                .date(workDetail.getDate())
                .name(workDetail.getName())
                .build();
    }

    private HolidayCalcDto weekdayHoliday(WorkDetail workDetail) {

        // 퇴근시간 - 출근시간 = 음수 => 출근시간을 0으로 바꿔서 계산
        // 퇴근시간 - 출근시간 = 양수 && 6시간을 Over 하면 0시간 으로 계산
        // 그외 새벽 시간(00:00 ~ 06:00)은 퇴근시간 - 출근시간 값으로 계산
        int workTime = workDetail.getEndWork() - workDetail.getBeginWork();

        if(workTime < 0) { // 06:00 ~ 00:01
            workTime = workDetail.getEndWork();
        }else if((workDetail.getBeginWork() / 60) > 0 && (workDetail.getEndWork() / 60) <= 6){ // 06:01 ~ 23:59
            workTime = workDetail.getEndWork() - workDetail.getBeginWork();
        } else {
            workTime = 0;
        }

        if (workTime <= 480) {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .weekdayHoliday(workTime)
                    .build();
        } else {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .weekdayHoliday(workTime)
                    .holiday8hOver(workTime - 480)
                    .build();
        }
    }

    private HolidayCalcDto holidayWeekday(WorkDetail workDetail) {

        int workTime = 0;
        // 퇴근시간 - 출근시간 = 음수 => 휴일 근로시간 - "00:00 ~ 06:00 시 근무시간"
        // 퇴근시간 - 출근시간 = 양수 => 휴일 근로시간

        if(workDetail.getEndWork() - workDetail.getBeginWork() < 0) {
            workTime = workDetail.getHolidayWork() - workDetail.getEndWork();
        }else {
            workTime = workDetail.getHolidayWork();
        }

        if (workDetail.getHolidayWork() <= 480) {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .holidayWeekday(workTime)
                    .build();
        } else {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .holidayWeekday(workTime)
                    .holiday8hOver(workTime - 480)
                    .build();
        }
    }

    private HolidayCalcDto holidayHoliday(WorkDetail workDetail) {
        if (workDetail.getHolidayWork() <= 480) {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .holidayHoliday(workDetail.getHolidayWork())
                    .build();
        } else {
            return HolidayCalcDto.builder()
                    .date(workDetail.getDate())
                    .name(workDetail.getName())
                    .holidayHoliday(workDetail.getHolidayWork())
                    .holiday8hOver(workDetail.getHolidayWork() - 480)
                    .build();
        }
    }
}
