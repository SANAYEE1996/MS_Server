package com.ms.controller;

import com.ms.dto.ResponseDto;
import com.ms.dto.ScheduleDto;
import com.ms.entity.Calendar;
import com.ms.entity.Schedule;
import com.ms.service.CalendarService;
import com.ms.service.Converter;
import com.ms.service.ScheduleService;
import com.ms.service.ValidationCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final CalendarService calendarService;

    private final ScheduleService scheduleService;

    private final ValidationCheck validationCheck;

    private final Converter converter;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody @Valid ScheduleDto scheduleDto){
        try {
            validationCheck.checkDate(scheduleDto);
            Calendar calendar = calendarService.findCalendar(calendarService.save(converter.toCalendar(scheduleDto)));
            scheduleService.save(new Schedule(0L, calendar, scheduleDto.getStartHour(), scheduleDto.getStartMin(), scheduleDto.getEndHour(), scheduleDto.getEndMin(), scheduleDto.getTitle(), scheduleDto.getNote()));
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(404).message("save fail").build();
        }
        return ResponseDto.builder().code(200).message("save success").build();
    }
}
