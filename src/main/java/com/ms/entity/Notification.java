package com.ms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "notification")
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "value")
    private Integer value;

    @Column(name = "notificate_time")
    private String time;
}
