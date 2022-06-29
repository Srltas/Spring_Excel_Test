package com.example.demo.repository;

import com.example.demo.domain.WorkDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.example.demo.util.DateTimeUtils.timestampOf;

@RequiredArgsConstructor
@Repository
public class UploadRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(List<WorkDetailDto> list) {

        for (WorkDetailDto workDetail : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO sample(seq,date,name,begin_work,end_work,total_work,night_work,holiday_work,leave,holiday_check) VALUES (null,?,?,?,?,?,?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(workDetail.getDate()));
                ps.setString(2, workDetail.getName());
                ps.setInt(3, workDetail.getBeginWork());
                ps.setInt(4, workDetail.getEndWork());
                ps.setInt(5, workDetail.getTotalWork());
                ps.setInt(6, workDetail.getNightWork());
                ps.setInt(7, workDetail.getHolidayWork());
                ps.setInt(8, workDetail.getLeave());
                ps.setString(9, workDetail.getHolidayCheck());

                return ps;
            });
        }
    }
}
