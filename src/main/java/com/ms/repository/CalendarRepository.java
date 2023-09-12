package com.ms.repository;

import com.ms.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByMemberIdAndYearAndMonthAndDay(Long memberId, Integer year, Integer month, Integer day);
}
