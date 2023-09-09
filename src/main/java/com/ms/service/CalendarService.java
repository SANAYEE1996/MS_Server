package com.ms.service;

import com.ms.entity.Calendar;
import com.ms.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void save(Calendar calendar) throws RuntimeException{
        if(calendarRepository.existsByMemberIdAndYearAndMonthAndDay(calendar.getMemberId(), calendar.getYear(), calendar.getMonth(), calendar.getDay())){
            throw new RuntimeException("already exists day please do not save. Do update !");
        }
        calendarRepository.save(calendar);
    }

    public Calendar findCalendar(Long id) throws RuntimeException{
        return calendarRepository.findById(id).orElseThrow(()-> new RuntimeException(id+"is not exists in table"));
    }
}
