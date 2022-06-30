package com.example.demo.repository;

import com.example.demo.domain.HolidayCalcDto;
import com.example.demo.domain.UserDto;
import com.example.demo.domain.WorkDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.example.demo.util.DateTimeUtils.timestampOf;

@RequiredArgsConstructor
@Repository
public class HolidayRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<UserDto> findAll() {
        return jdbcTemplate.query("SELECT seq, name FROM users ORDER BY name",mapper);
    }

    public void save(List<HolidayCalcDto> list) {

        for (HolidayCalcDto holidayCalcDto : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO holiday_calc(seq,date,name,holiday_holiday,holiday_weekday,weekday_holiday,holiday_8H_Over) VALUES (null,?,?,?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(holidayCalcDto.getDate()));
                ps.setString(2, holidayCalcDto.getName());
                ps.setInt(3, holidayCalcDto.getHolidayHoliday());
                ps.setInt(4, holidayCalcDto.getHolidayWeekday());
                ps.setInt(5, holidayCalcDto.getWeekdayHoliday());
                ps.setInt(6, holidayCalcDto.getHoliday8hOver());

                return ps;
            });
        }
    }

    static RowMapper<UserDto> mapper = (rs, rowNum) -> UserDto.builder()
            .seq(rs.getLong("seq"))
            .name(rs.getString("name"))
            .build();
}
