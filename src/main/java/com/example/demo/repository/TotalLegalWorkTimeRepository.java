package com.example.demo.repository;

import com.example.demo.domain.TotalLegalWorkTime;
import com.example.demo.domain.TotalLegalWorkTimeDto;
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
public class TotalLegalWorkTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<TotalLegalWorkTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM total_legal_work_time", mapper);
    }

    public void save(List<TotalLegalWorkTimeDto> list) {
        for (TotalLegalWorkTimeDto twtd : list) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO total_legal_work_time(date,quarter,total_work_time,legal_work_time) VALUES (?,?,?,?)", new String[]{"seq"});
                ps.setDate(1, timestampOf(twtd.getDate()));
                ps.setString(2, twtd.getQuarter());
                ps.setInt(3, twtd.getTotalWorkTime());
                ps.setInt(4, twtd.getLegalWorkTime());

                return ps;
            });
        }
    }

    static RowMapper<TotalLegalWorkTime> mapper = (rs, rowNum) -> TotalLegalWorkTime.builder()
            .seq(rs.getLong("seq"))
            .date(dateTimeOf(rs.getDate("date")))
            .quarter(rs.getString("quarter"))
            .totalWorkTime(rs.getInt("total_work_time"))
            .legalWorkTime(rs.getInt("legal_work_time"))
            .build();
}
