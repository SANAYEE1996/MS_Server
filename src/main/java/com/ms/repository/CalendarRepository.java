package com.ms.repository;

import com.ms.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    boolean existsByMemberIdAndYearAndMonthAndDay(Long memberId, Integer year, Integer month, Integer day);
}
