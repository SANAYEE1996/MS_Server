package com.ms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "notification")
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

    @Column("value")
    private Integer value;

    @Column("time")
    private String time;
}