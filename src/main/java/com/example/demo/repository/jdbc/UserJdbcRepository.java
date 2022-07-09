package com.example.demo.repository.jdbc;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT seq, name FROM users ORDER BY name",mapper);
    }

    static RowMapper<User> mapper = (rs, rowNum) -> User.builder()
            .seq(rs.getLong("seq"))
            .name(rs.getString("name"))
            .build();
}
