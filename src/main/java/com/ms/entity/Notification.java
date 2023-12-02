package com.ms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("notification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private Long id;

    @Column("schedule_id")
    private Long scheduleId;

    @Column("type")
    private NotificationType notificationType;

    private Integer value;

    @Column("notification_time")
    private String notificationTime;
}