package com.ms.repository;

import com.ms.entity.Calendar;
import com.ms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    boolean existsByMemberAndYearAndMonthAndDay(Member member, Integer year, Integer month, Integer day);
}
