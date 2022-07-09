package com.example.demo.repository;

import com.example.demo.domain.WorkDetail;
import com.example.demo.domain.WorkDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadRepository {

    List<WorkDetail> findAll();

    List<WorkDetail> find(String name);

    List<WorkDetail> findAdmin();

    void save(List<WorkDetailDto> list);
}
