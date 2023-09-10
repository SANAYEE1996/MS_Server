package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.entity.Calendar;
import org.springframework.stereotype.Service;

@Service
public class Converter {

    public Calendar toCalendar(ScheduleDto calendarDto){
        return new Calendar(0L, calendarDto.getMemberId(), calendarDto.getYear(), calendarDto.getMonth(), calendarDto.getDay());
    }
}
