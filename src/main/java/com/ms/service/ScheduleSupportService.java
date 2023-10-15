package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.repository.ScheduleRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleSupportService {

    private final ScheduleRepositorySupport scheduleRepositorySupport;

    public ScheduleDto findScheduleForDay(ScheduleRequestDto scheduleRequestDto){
        return scheduleRepositorySupport.findScheduleListForDay(scheduleRequestDto);
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto){
        return scheduleRepositorySupport.findScheduleListForMonth(scheduleRequestDto);
    }

    public void updateSchedule(ScheduleDto scheduleDto){
        scheduleRepositorySupport.updateSchedule(scheduleDto);
    }
}
