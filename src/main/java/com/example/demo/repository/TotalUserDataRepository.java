package com.example.demo.repository;

import com.example.demo.domain.TotalUserData;
import com.example.demo.domain.TotalUserDataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TotalUserDataRepository {

    List<TotalUserData> findAll();

    void save(List<TotalUserDataDto> list);
}
