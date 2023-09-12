package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.Calendar;
import com.ms.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombineService {

    private final CalendarService calendarService;

    private final ScheduleService scheduleService;

    private final ScheduleFindService scheduleFindService;

    private final ValidationCheck validationCheck;

    private final Converter converter;

    public void saveSchedule(ScheduleDto scheduleDto) throws RuntimeException{
        validationCheck.checkDate(scheduleDto);
        Long calendarId = (scheduleDto.getCalendarId() != null) ? scheduleDto.getCalendarId() : calendarService.save(converter.toCalendar(scheduleDto));
        Calendar calendar = calendarService.findCalendar(calendarId);
        scheduleService.save(new Schedule(0L, calendar, scheduleDto.getStartHour(), scheduleDto.getStartMin(), scheduleDto.getEndHour(), scheduleDto.getEndMin(), scheduleDto.getTitle(), scheduleDto.getNote()));
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        return scheduleFindService.findScheduleForMonth(scheduleRequestDto);
    }
}
