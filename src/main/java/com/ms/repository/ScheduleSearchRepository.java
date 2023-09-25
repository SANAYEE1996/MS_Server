package com.ms.repository;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ms.entity.QSchedule.schedule;
import static com.ms.entity.QColor.color;

@Slf4j
@Repository
public class ScheduleSearchRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ScheduleSearchRepository(JPAQueryFactory queryFactory){
        super(ScheduleDto.class);
        this.queryFactory = queryFactory;
    }

    public ScheduleDto findScheduleListForDay(ScheduleRequestDto scheduleRequestDto){
        return listInitial()
               .where(schedule.id.eq(scheduleRequestDto.getScheduleId()))
               .fetchOne();
    }

    public List<ScheduleDto> findScheduleListForMonth(ScheduleRequestDto scheduleRequestDto){
        return listInitial()
                .where(schedule.memberId.eq(scheduleRequestDto.getMemberId())
                        .and(schedule.startYear.eq(scheduleRequestDto.getYear()).or(schedule.endYear.eq(scheduleRequestDto.getYear())))
                        .and(schedule.startMonth.eq(scheduleRequestDto.getMonth()).or(schedule.endMonth.eq(scheduleRequestDto.getMonth()))))
               .fetch();
    }

    private JPAQuery<ScheduleDto> listInitial(){
        return queryFactory.select(Projections.constructor(ScheduleDto.class,
                        schedule.id.as("scheduleId"),
                        schedule.memberId.as("memberId"),
                        color.id.as("colorId"),
                        color.name.as("colorName"),
                        schedule.startYear.as("startYear"),
                        schedule.startMonth.as("startMonth"),
                        schedule.startDay.as("startDay"),
                        schedule.startHour.as("startHour"),
                        schedule.startMin.as("startMin"),
                        schedule.endYear.as("endYear"),
                        schedule.endMonth.as("endMonth"),
                        schedule.endDay.as("endDay"),
                        schedule.endHour.as("endHour"),
                        schedule.endMin.as("endMin"),
                        schedule.location.as("location"),
                        schedule.title.as("title"),
                        schedule.note.as("note")
                ))
                .from(schedule)
                .innerJoin(schedule.color, color);
    }
}
