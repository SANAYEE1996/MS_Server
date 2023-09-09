package com.ms.service;

import com.ms.dto.CalendarDto;
import com.ms.entity.Calendar;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public Calendar toCalendar(CalendarDto calendarDto){
        return new Calendar(0L, calendarDto.getMemberId(), calendarDto.getYear(), calendarDto.getMonth(), calendarDto.getDay());
    }
}
