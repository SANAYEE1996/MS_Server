package com.ms.service;

import com.ms.entity.Schedule;
import com.ms.repository.ScheduleNativeRepository;
import com.ms.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleNativeRepository scheduleNativeRepository;

    public void save(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public void delete(Long schedule_id){
        scheduleNativeRepository.deleteById(schedule_id);
    }
}
