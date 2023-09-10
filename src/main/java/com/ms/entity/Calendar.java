package com.ms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "calendar")
@Getter
@NoArgsConstructor
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @OneToMany(mappedBy =  "calendar")
    private List<Schedule> scheduleList = new ArrayList<>();

    public Calendar(Long id, Long memberId, Integer year, Integer month, Integer day){
        this.id = id;
        this.memberId = memberId;
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
