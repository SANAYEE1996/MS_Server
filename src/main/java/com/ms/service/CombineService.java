package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.Notification;
import com.ms.repository.ScheduleRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombineService {

    private final ScheduleService scheduleService;

    private final ScheduleRepositorySupport scheduleRepositorySupport;

    private final NotificationService notificationService;

    private final ColorService colorService;

    private final Converter converter;

    private TimeCalculateService timeCalculateService;

    public Mono<String> saveSchedule(ScheduleDto scheduleDto) {
        timeCalculateService = new TimeCalculateService(scheduleDto);
        return colorService.findColor(scheduleDto.getColorId())
                .flatMap(color -> scheduleService.save(converter.toSchedule(scheduleDto, color.getId())))
                .flatMap(schedule -> notificationService.saveAll(converter.toNotificationList(scheduleDto.getNotificationDtoList(), schedule.getId(), timeCalculateService)))
                .flatMap(result -> Mono.just("save success"));
    }

    public Mono<ScheduleDto> findScheduleForDay(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        return Mono.zip(
                scheduleRepositorySupport.findScheduleListForDay(scheduleRequestDto),
                notificationService.getNotificationList(scheduleRequestDto.getScheduleId())
                ).map(req -> {
                    ScheduleDto dto = req.getT1();
                    List<Notification> list = req.getT2();
                    dto.setNotification(converter.toNotificationDtoList(list));
                    return dto;
                });
    }

    public Mono<List<ScheduleDto>> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        return scheduleRepositorySupport.findScheduleListForMonth(scheduleRequestDto);
    }

    public Mono<String> deleteSchedule(Long schedule_id) throws RuntimeException{
        return notificationService.deleteByScheduleId(schedule_id)
                .then(Mono.defer(() -> scheduleService.delete(schedule_id)))
                .then(Mono.defer(() -> Mono.just("delete success")));
    }

    public Mono<String> updateSchedule(ScheduleDto scheduleDto) throws RuntimeException{
        timeCalculateService = new TimeCalculateService(scheduleDto);
        return Mono.zip(colorService.findColor(scheduleDto.getColorId()), scheduleService.getSchedule(scheduleDto.getScheduleId()))
                .flatMap(req -> scheduleService.save(converter.toScheduleForUpdate(req.getT2(), scheduleDto)))
                .flatMap(schedule -> notificationService.saveAll(converter.toNotificationList(scheduleDto.getNotificationDtoList(), schedule.getId(), timeCalculateService)))
                .flatMap(result -> Mono.just("update success"));
    }
}
