package com.ms.repository;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import com.ms.entity.Schedule;
import org.springframework.stereotype.Repository;

@Repository("scheduleQueryDslRepository")
public interface ScheduleQueryDslRepository extends QuerydslR2dbcRepository<Schedule, Long> {
}
