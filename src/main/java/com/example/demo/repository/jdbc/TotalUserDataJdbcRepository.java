package com.example.demo.repository.jdbc;

import com.example.demo.domain.TotalUserData;
import com.example.demo.domain.TotalUserDataDto;
import com.example.demo.repository.TotalUserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
public class TotalUserDataJdbcRepository implements TotalUserDataRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<TotalUserData> findAll() {
        return jdbcTemplate.query("SELECT * FROM total_user_data", mapper);
    }

    public void save(List<TotalUserDataDto> list) {
        for (TotalUserDataDto totalUserDataDto : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO total_user_data(quarter,name,quarter_work,prescribed_over_work,legal_over_work,night_work,holiday_work,holiday_8H_over,leave,compensation_leave,quarter_money,quarter_total) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", new String[]{"seq"});
                ps.setString(1, totalUserDataDto.getQuarter());
                ps.setString(2, totalUserDataDto.getName());
                ps.setInt(3, totalUserDataDto.getQuarterWork());
                ps.setInt(4, totalUserDataDto.getPrescribedOverWork());
                ps.setInt(5, totalUserDataDto.getLegalOverWork());
                ps.setInt(6, totalUserDataDto.getNightWork());
                ps.setInt(7, totalUserDataDto.getHolidayWork());
                ps.setInt(8, totalUserDataDto.getHoliday8HOver());
                ps.setInt(9, totalUserDataDto.getLeave());
                ps.setInt(10, totalUserDataDto.getCompensationLeave());
                ps.setInt(11, totalUserDataDto.getQuarterMoney());
                ps.setInt(12, totalUserDataDto.getQuarterTotal());

                return ps;
            });
        }
    }



    static RowMapper<TotalUserData> mapper = (rs, rowNum) -> TotalUserData.builder()
            .seq(rs.getLong("seq"))
            .quarter(rs.getString("quarter"))
            .name(rs.getString("name"))
            .quarterWork(rs.getInt("quarter_work"))
            .prescribedOverWork(rs.getInt("prescribed_over_work"))
            .legalOverWork(rs.getInt("legal_over_work"))
            .nightWork(rs.getInt("night_work"))
            .holidayWork(rs.getInt("holiday_work"))
            .holiday8HOver(rs.getInt("holiday_8H_over"))
            .leave(rs.getInt("leave"))
            .compensationLeave(rs.getInt("compensation_leave"))
            .quarterMoney(rs.getInt("quarter_money"))
            .quarterTotal(rs.getInt("quarter_total"))
            .build();
}
