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
import static com.ms.entity.QCalendar.calendar;

@Slf4j
@Repository
public class ScheduleSupportRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ScheduleSupportRepository(JPAQueryFactory queryFactory){
        super(ScheduleDto.class);
        this.queryFactory = queryFactory;
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto dto){
        return columns()
               .where(calendar.memberId.eq(dto.getMemberId())
                   .and(calendar.year.eq(dto.getYear()))
                   .and(calendar.month.eq(dto.getMonth())))
                .orderBy(calendar.day.asc(),schedule.startHour.asc())
               .fetch();
    }

    private JPAQuery<ScheduleDto> columns(){
        return queryFactory.select(Projections.constructor(ScheduleDto.class,
                    calendar.id.as("calendarId"),
                    calendar.memberId.as("memberId"),
                    calendar.year.as("year"),
                    calendar.month.as("month"),
                    calendar.day.as("day"),
                    schedule.id.as("scheduleId"),
                    schedule.startHour.as("startHour"),
                    schedule.startMin.as("startMin"),
                    schedule.endHour.as("endHour"),
                    schedule.endMin.as("endMin"),
                    schedule.title.as("title"),
                    schedule.note.as("note")
                ))
                .from(calendar)
                .leftJoin(calendar.scheduleList, schedule);
    }
}
