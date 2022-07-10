package com.example.demo.repository.jdbc;

import com.example.demo.domain.WorkDetail;
import com.example.demo.domain.WorkDetailDto;
import com.example.demo.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.util.List;

import static com.example.demo.util.DateTimeUtils.dateTimeOf;
import static com.example.demo.util.DateTimeUtils.timestampOf;

@RequiredArgsConstructor
public class UploadJdbcRepository implements UploadRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<WorkDetail> findAll() {
        return jdbcTemplate.query("SELECT * FROM sample", mapper);
    }

    public List<WorkDetail> find(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM sample WHERE name=? ORDER BY date",
                mapper,
                name
        );
    }

    public List<WorkDetail> findAdmin() {
        return jdbcTemplate.query("SELECT * FROM sample WHERE name='admin'", mapper);
    }

    public void save(List<WorkDetailDto> list) {

        for (WorkDetailDto workDetail : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO sample(date,day_of_the_week,name,begin_work,end_work,total_work,night_work,holiday_work,leave,holiday_check) VALUES (?,?,?,?,?,?,?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(workDetail.getDate()));
                ps.setString(2, workDetail.getDayOfTheWeek());
                ps.setString(3, workDetail.getName());
                ps.setInt(4, workDetail.getBeginWork());
                ps.setInt(5, workDetail.getEndWork());
                ps.setInt(6, workDetail.getTotalWork());
                ps.setInt(7, workDetail.getNightWork());
                ps.setInt(8, workDetail.getHolidayWork());
                ps.setInt(9, workDetail.getLeave());
                ps.setString(10, workDetail.isHoliday() ? "O" : "X");

                return ps;
            });
        }
    }

    static RowMapper<WorkDetail> mapper = (rs, rowNum) -> WorkDetail.builder()
            .seq(rs.getLong("seq"))
            .date(dateTimeOf(rs.getDate("date")))
            .dayOfTheWeek(rs.getString("day_of_the_week"))
            .name(rs.getString("name"))
            .beginWork(rs.getInt("begin_work"))
            .endWork(rs.getInt("end_work"))
            .totalWork(rs.getInt("total_work"))
            .nightWork(rs.getInt("night_work"))
            .holidayWork(rs.getInt("holiday_work"))
            .leave(rs.getInt("leave"))
            .holiday(rs.getString("holiday_check").equals("O") ? true : false)
            .build();
}
