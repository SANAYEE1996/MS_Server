package com.ms.service;

import com.ms.entity.Schedule;
import com.ms.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule){
        return scheduleRepository.save(schedule).getId();
    }
}
