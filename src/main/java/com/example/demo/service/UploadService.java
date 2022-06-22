package com.example.demo.service;

import com.example.demo.domain.WorkDetail;
import com.example.demo.repository.UploadRepository;
import com.example.demo.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final ExcelUtil excelUtil;

    private final UploadRepository uploadRepository;

    public void upload(MultipartFile file) {
        ArrayList<WorkDetail> workDetails = new ArrayList<>();

        List<Map<String, String>> listData = excelUtil.getListData(file, 4, 26);

        List<WorkDetail> list = mapper(listData);

        log.info("list : {}", list);

        uploadRepository.save(list);

    }

    private List<WorkDetail> mapper(List<Map<String, String>> listData) {

        ArrayList<WorkDetail> list = new ArrayList<WorkDetail>();

        for (Map<String, String> data : listData) {

            String workingTime = data.get("14").split("/")[0];


            list.add(WorkDetail.builder()
                    .day(data.get("0"))
                    .dayOfTheWeek(data.get("1"))
                    .name(data.get("2"))
                    .goOfficeTime(data.get("7"))
                    .leaveOfficeTime(data.get("8"))
                    .checkInTime(data.get("9"))
                    .goalWorkingTime(data.get("11"))
                    .workingTime(workingTime)
                    .build());
        }

        return list;
    }
}
