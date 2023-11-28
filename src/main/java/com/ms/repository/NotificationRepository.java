package com.ms.repository;

import com.ms.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from notification n where n.schedule.id = :scheduleId")
    List<Notification> findAllByMyWay(Long scheduleId);

    @Transactional
    @Modifying
    @Query("delete from notification n where n.schedule.id = :scheduleId")
    void deleteForUpdateByScheduleId(Long scheduleId);
}
