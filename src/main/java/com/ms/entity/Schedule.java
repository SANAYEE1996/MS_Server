package com.ms.entity;

import com.ms.dto.ScheduleDto;
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

    @Column(name= "start_day")
    private Integer startDay;

    @Column(name = "start_hour")
    private Integer startHour;

    @Column(name = "start_min")
    private Integer startMin;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "end_month")
    private Integer endMonth;

    @Column(name= "end_day")
    private Integer endDay;

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

    public Schedule(Color color, ScheduleDto scheduleDto) {
        this.color = color;
        this.memberId = scheduleDto.getMemberId();
        this.startYear = scheduleDto.getStartYear();
        this.startMonth = scheduleDto.getStartMonth();
        this.startHour = scheduleDto.getStartHour();
        this.startMin = scheduleDto.getStartMin();
        this.endYear = scheduleDto.getEndYear();
        this.endMonth = scheduleDto.getEndMonth();
        this.endHour = scheduleDto.getEndHour();
        this.endMin = scheduleDto.getEndMin();
        this.location = scheduleDto.getLocation();
        this.title = scheduleDto.getTitle();
        this.note = scheduleDto.getNote();
    }

    @OneToMany(mappedBy = "schedule")
    private List<Notification> notificationList = new ArrayList<>();
}
