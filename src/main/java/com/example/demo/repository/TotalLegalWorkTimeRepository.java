package com.example.demo.repository;

import com.example.demo.domain.TotalLegalWorkTime;
import com.example.demo.domain.TotalLegalWorkTimeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TotalLegalWorkTimeRepository {

    List<TotalLegalWorkTime> findAll();

    void save(List<TotalLegalWorkTimeDto> list);
}
