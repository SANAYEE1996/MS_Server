package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.repository.ScheduleSupportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleFindService {

    private final ScheduleSupportRepository scheduleSupportRepository;

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto dto){
        return scheduleSupportRepository.findScheduleForMonth(dto);
    }

}
