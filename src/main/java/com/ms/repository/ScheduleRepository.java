package com.ms.repository;

import com.ms.entity.Schedule;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("scheduleRepository")
public interface ScheduleRepository extends ReactiveCrudRepository<Schedule, Long> {
}
