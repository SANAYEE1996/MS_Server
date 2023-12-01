package com.ms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    private Long id;

    @Column("color_id")
    private Long colorId;

    @Column("member_id")
    private Long memberId;

    @Column("start_year")
    private Integer startYear;

    @Column("start_month")
    private Integer startMonth;

    @Column("start_day")
    private Integer startDay;

    @Column("start_hour")
    private Integer startHour;

    @Column("start_min")
    private Integer startMin;

    @Column("end_year")
    private Integer endYear;

    @Column("end_month")
    private Integer endMonth;

    @Column("end_day")
    private Integer endDay;

    @Column("end_hour")
    private Integer endHour;

    @Column("end_min")
    private Integer endMin;

    @Column("location")
    private String location;

    @Column("title")
    private String title;

    @Column("note")
    private String note;
}
