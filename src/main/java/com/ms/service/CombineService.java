package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.Color;
import com.ms.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombineService {

    private final ScheduleService scheduleService;

    private final ColorService colorService;

    public void saveSchedule(ScheduleDto scheduleDto) throws RuntimeException{
        ScheduleValidationCheck.getInstance(scheduleDto).check();
        Color color = colorService.findColor(scheduleDto.getColorId());
        Schedule schedule = new Schedule(color, scheduleDto);
        scheduleService.save(schedule);
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{

        return new ArrayList<>();
    }

    public void deleteSchedule(Long schedule_id){

    }
}
