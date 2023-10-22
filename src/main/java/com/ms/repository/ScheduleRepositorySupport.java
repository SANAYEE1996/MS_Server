package com.ms.repository;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.ms.entity.QSchedule.schedule;
import static com.ms.entity.QColor.color;

@Slf4j
@Repository
public class ScheduleRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ScheduleRepositorySupport(JPAQueryFactory queryFactory){
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

    @Modifying
    @Transactional
    public void updateSchedule(ScheduleDto scheduleDto){
        UpdateClause<JPAUpdateClause> builder = update(schedule);
        updateSetting(scheduleDto,builder);
        builder.where(schedule.id.eq(scheduleDto.getScheduleId())).execute();
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

    private void updateSetting(ScheduleDto scheduleDto, UpdateClause<JPAUpdateClause> builder){
        builder.set(schedule.color.id, scheduleDto.getColorId());
        builder.set(schedule.startYear, scheduleDto.getStartYear());
        builder.set(schedule.startMonth, scheduleDto.getStartMonth());
        builder.set(schedule.startDay, scheduleDto.getStartDay());
        builder.set(schedule.startHour, scheduleDto.getStartHour());
        builder.set(schedule.startMin, scheduleDto.getStartMin());
        builder.set(schedule.endYear, scheduleDto.getEndYear());
        builder.set(schedule.endMonth, scheduleDto.getEndMonth());
        builder.set(schedule.endDay, scheduleDto.getEndDay());
        builder.set(schedule.endHour, scheduleDto.getEndHour());
        builder.set(schedule.endMin, scheduleDto.getEndMin());
        builder.set(schedule.title, scheduleDto.getTitle());
        builder.set(schedule.note, scheduleDto.getNote());
        builder.set(schedule.location, StringUtils.hasText(scheduleDto.getLocation()) ? scheduleDto.getLocation() : "");
    }
}
