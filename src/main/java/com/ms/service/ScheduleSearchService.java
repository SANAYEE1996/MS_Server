package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.repository.ScheduleSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleSearchService {

    private final ScheduleSearchRepository scheduleSearchRepository;

    public ScheduleDto findScheduleForDay(ScheduleRequestDto scheduleRequestDto){
        return scheduleSearchRepository.findScheduleListForDay(scheduleRequestDto);
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto){
        return scheduleSearchRepository.findScheduleListForMonth(scheduleRequestDto);
    }
}
