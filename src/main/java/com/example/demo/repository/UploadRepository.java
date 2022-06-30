package com.example.demo.repository;

import com.example.demo.domain.TotalLegalWorkTimeDto;
import com.example.demo.domain.WorkDetail;
import com.example.demo.domain.WorkDetailDto;
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
public class UploadRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<WorkDetail> find(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM sample WHERE name=? ORDER BY date",
                mapper,
                name
                );
    }

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
                ps.setString(9, workDetail.isHoliday() ? "O" : "X");

                return ps;
            });
        }
    }

    public void TotalLegalWorkTimeSave(List<TotalLegalWorkTimeDto> list) {

        for (TotalLegalWorkTimeDto twtd : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO total_legal_work_time(seq,date,quarter,total_work_time,legal_work_time) VALUES (null,?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(twtd.getDate()));
                ps.setString(2, twtd.getQuarter());
                ps.setInt(3, twtd.getTotalWorkTime());
                ps.setInt(4, twtd.getLegalWorkTime());

                return ps;
            });
        }
    }

    static RowMapper<WorkDetail> mapper = (rs, rowNum) -> WorkDetail.builder()
            .seq(rs.getLong("seq"))
            .date(dateTimeOf(rs.getDate("date")))
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
