package com.ms.service;

import com.ms.entity.Calendar;
import com.ms.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Long save(Calendar calendar) throws RuntimeException{
        Optional<Calendar> checkCalendar = calendarRepository.findByMemberIdAndYearAndMonthAndDay(calendar.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
        if(checkCalendar.isPresent()){
            return checkCalendar.get().getId();
        }
        return calendarRepository.save(calendar).getId();
    }

    public Calendar findCalendar(Long id) throws RuntimeException{
        return calendarRepository.findById(id).orElseThrow(()-> new RuntimeException(id+"is not exists in table"));
    }
}
