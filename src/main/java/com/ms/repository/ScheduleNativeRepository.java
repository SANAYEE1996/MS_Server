package com.ms.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ScheduleNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public void deleteById(Long schedule_id){
        String sql = "DELETE FROM schedule WHERE ID = ? ";

        jdbcTemplate.update(sql, schedule_id);
    }
}
