package com.example.demo.repository;

import com.example.demo.domain.HolidayCalc;
import com.example.demo.domain.HolidayCalcDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.example.demo.util.DateTimeUtils.dateTimeOf;
import static com.example.demo.util.DateTimeUtils.timestampOf;

@RequiredArgsConstructor
@Repository
public class HolidayRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<HolidayCalc> findAll() {
        return jdbcTemplate.query("SELECT * FROM holiday_calc", mapper);
    }

    public List<HolidayCalc> find(String name) {
        return jdbcTemplate.query("SELECT * FROM holiday_calc WHERE name=?", mapper, name);
    }

    public void save(List<HolidayCalcDto> list) {
        for (HolidayCalcDto holidayCalcDto : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO holiday_calc(date,name,holiday_holiday,holiday_weekday,weekday_holiday,holiday_8H_Over) VALUES (?,?,?,?,?,?)", new String[]{"seq"});
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

    static RowMapper<HolidayCalc> mapper = (rs, rowNum) -> HolidayCalc.builder()
            .seq(rs.getLong("seq"))
            .date(dateTimeOf(rs.getDate("date")))
            .name(rs.getString("name"))
            .holidayHoliday(rs.getInt("holiday_holiday"))
            .weekdayHoliday(rs.getInt("weekday_holiday"))
            .build();
}
