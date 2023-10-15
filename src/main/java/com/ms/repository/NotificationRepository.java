package com.ms.repository;

import com.ms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from notification n where n.schedule.id = :scheduleId")
    List<Notification> findAllByMyWay(Long scheduleId);

    @Modifying
    @Query("delete from notification n where n.schedule.id = :scheduleId")
    void deleteForUpdateByScheduleId(Long scheduleId);
}
