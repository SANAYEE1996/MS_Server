package com.ms.repository;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.querydsl.sql.SQLQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.ms.entity.QSchedule.schedule;
import static com.ms.entity.QColor.color;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.sql.SQLExpressions.select;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleRepositorySupport {

    private final ScheduleQueryDslRepository repository;

    public Mono<ScheduleDto> findScheduleListForDay(ScheduleRequestDto scheduleRequestDto){
        return repository
                .query(query -> listInitial().where(schedule.id.eq(scheduleRequestDto.getScheduleId()))).one();
    }

    public Mono<List<ScheduleDto>> findScheduleListForMonth(ScheduleRequestDto scheduleRequestDto){
        return repository
                .query(query -> listInitial().where(schedule.memberId.eq(scheduleRequestDto.getMemberId())
                        .and(schedule.startYear.eq(scheduleRequestDto.getYear()).or(schedule.endYear.eq(scheduleRequestDto.getYear())))
                        .and(schedule.startMonth.eq(scheduleRequestDto.getMonth()).or(schedule.endMonth.eq(scheduleRequestDto.getMonth()))))).all().collectList();
    }

    private SQLQuery<ScheduleDto> listInitial(){
        return select(constructor(ScheduleDto.class,
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
                schedule.note.as("note")))
                .from(schedule).innerJoin(color).on(schedule.colorId.eq(color.id));
    }
}
