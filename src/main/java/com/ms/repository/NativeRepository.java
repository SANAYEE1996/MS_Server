package com.ms.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public void deleteNotificationByScheduleId(Long scheduleId){
        String sql = "DELETE FROM notification WHERE schedule_id = ? ";

        jdbcTemplate.update(sql, scheduleId);
    }

    public void deleteScheduleById(Long scheduleId){
        String sql = "DELETE FROM schedule WHERE ID = ? ";

        jdbcTemplate.update(sql, scheduleId);
    }
}
