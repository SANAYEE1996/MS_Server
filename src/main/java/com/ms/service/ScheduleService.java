package com.ms.service;

import com.ms.entity.Schedule;
import com.ms.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public Mono<Schedule> save(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public Mono<Void> delete(Long scheduleId){
        return isIdExists(scheduleId).flatMap(isExistsId -> {
            if(isExistsId){
                return scheduleRepository.deleteById(scheduleId);
            }
            return Mono.error(new RuntimeException(scheduleId + "is not exists id !"));
        });
    }

    public Mono<Schedule> getSchedule(Long id){
        return isIdExists(id).flatMap(isIdExists -> {
            if(isIdExists){
                return scheduleRepository.findById(id);
            }
            return Mono.error(new RuntimeException(id + "is not exists id !"));
        });
    }

    public Mono<List<Schedule>> findScheduleForMonth(long memberId, int year, int month){
        return scheduleRepository.findAllScheduleForMonth(memberId, year, month).collectList();
    }

    public Mono<Boolean> isIdExists(Long id){
        return scheduleRepository.existsById(id);
    }
}
