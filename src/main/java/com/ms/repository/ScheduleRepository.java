package com.ms.repository;

import com.ms.entity.Schedule;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository("scheduleRepository")
public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, Long> {

    @Query( "select schedule.* from schedule " +
            "where schedule.member_id = :memberId " +
            "and (schedule.start_year = :year or schedule.end_year = :year) " +
            "and (schedule.start_month = :month or schedule.end_month = :month)")
    Flux<Schedule> findAllScheduleForMonth(long memberId, int year, int month);
}
