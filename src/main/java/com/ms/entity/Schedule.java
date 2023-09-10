package com.ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_min")
    private Integer startMin;

    @Column(name = "end_hour")
    private Integer endHour;

    @Column(name = "end_min")
    private Integer endMin;

    @Column(name="title")
    private String title;

    @Column(name="note")
    private String note;
}
