package com.example.demo.repository;

import com.example.demo.domain.HolidayCalc;
import com.example.demo.domain.HolidayCalcDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HolidayRepository {

    List<HolidayCalc> findAll();

    List<HolidayCalc> find(String name);

    void save(List<HolidayCalcDto> list);
}
