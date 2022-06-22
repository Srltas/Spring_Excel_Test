package com.example.demo.repository;

import com.example.demo.domain.WorkDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UploadRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(List<WorkDetail> list) {

        for (WorkDetail workDetail : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO sample values(?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, workDetail.getDay());
                ps.setString(2, workDetail.getDayOfTheWeek());
                ps.setString(3, workDetail.getName());
                ps.setString(4, workDetail.getGoOfficeTime());
                ps.setString(5, workDetail.getLeaveOfficeTime());
                ps.setString(6, workDetail.getCheckInTime());
                ps.setString(7, workDetail.getGoalWorkingTime());
                ps.setString(8, workDetail.getWorkingTime());
                return ps;
            });
        }
    }
}
