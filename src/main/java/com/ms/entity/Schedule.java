package com.ms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "start_month")
    private Integer startMonth;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_min")
    private Integer startMin;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "end_month")
    private Integer endMonth;

    @Column(name = "end_hour")
    private Integer endHour;

    @Column(name = "end_min")
    private Integer endMin;

    @Column(name = "location")
    private String location;

    @Column(name = "title")
    private String title;

    @Column(name = "note")
    private String note;

    public Schedule(Long id, Color color, Long memberId, Integer startYear, Integer startMonth, Integer startHour, Integer startMin, Integer endYear, Integer endMonth, Integer endHour, Integer endMin, String location, String title, String note) {
        this.id = id;
        this.color = color;
        this.memberId = memberId;
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.endHour = endHour;
        this.endMin = endMin;
        this.location = location;
        this.title = title;
        this.note = note;
    }

    @OneToMany(mappedBy = "schedule")
    private List<Notification> notificationList = new ArrayList<>();
}
