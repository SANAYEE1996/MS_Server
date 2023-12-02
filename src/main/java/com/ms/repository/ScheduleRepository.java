package com.ms.repository;

import com.ms.entity.Schedule;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository("scheduleRepository")
public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, Long> {

    @Query( "select s from schedule s " +
            "where s.memberId = :memberId " +
            "and (s.startYear = :year or s.endYear = :year) " +
            "and (s.startMonth = :month or s.endMonth = :month)")
    Flux<Schedule> findAllScheduleForMonth(long memberId, int year, int month);
}
