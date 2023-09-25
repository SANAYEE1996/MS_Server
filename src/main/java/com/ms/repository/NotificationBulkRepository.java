package com.ms.repository;

import com.ms.dto.NotificationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<NotificationDto> notificationList){
        String sql = "INSERT INTO notification (schedule_id, name, notificate_time) " +
                     "VALUES (?, ?, ?, ?) ";

        jdbcTemplate.batchUpdate(sql,
                notificationList,
                notificationList.size(),
                (PreparedStatement ps, NotificationDto notificationDto) -> {
                    ps.setLong(1, notificationDto.getScheduleId());
                    ps.setString(2, notificationDto.getName());
                    ps.setString(3, notificationDto.getNotificationTime());
                });
    }
}
