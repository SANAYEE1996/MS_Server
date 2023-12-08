package com.ms.service;

import com.ms.dto.MemberInfoDto;
import com.ms.dto.NotificationServerDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.Notification;
import com.ms.entity.Schedule;
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

    private final NotificationService notificationService;

    private final ColorService colorService;

    private final Converter converter;

    private TimeCalculateService timeCalculateService;

    public Mono<Schedule> saveSchedule(ScheduleDto scheduleDto) {
        timeCalculateService = new TimeCalculateService(scheduleDto);
        return colorService.findColor(scheduleDto.getColorId())
                .flatMap(color -> scheduleService.save(converter.toSchedule(scheduleDto, color)))
                .flatMap(schedule ->
                        Mono.zip(
                                notificationService.saveAll(converter.toNotificationList(scheduleDto.getNotificationDtoList(), schedule.getId(), timeCalculateService)),
                                Mono.just(schedule)
                        )
                )
                .flatMap(result -> Mono.just(result.getT2()));
    }

    public Mono<ScheduleDto> findScheduleForDay(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        return Mono.zip(
                scheduleService.getSchedule(scheduleRequestDto.getScheduleId()),
                notificationService.getNotificationList(scheduleRequestDto.getScheduleId())
                ).map(req ->{
                    ScheduleDto dto = converter.toScheduleDto(req.getT1());
                    List<Notification> list = req.getT2();
                    dto.setNotification(converter.toNotificationDtoList(list));
                    return dto;
                });
    }

    public Mono<List<Notification>> findNotificationListByScheduleId(Long id){
        return notificationService.getNotificationList(id);
    }

    public List<NotificationServerDto> toNotificationServerDtoList(Schedule schedule, MemberInfoDto dto, List<Notification> list){
        return converter.toNotificationServerDtoList(schedule, dto, list);
    }

    public Mono<List<ScheduleDto>> findScheduleForMonth(ScheduleRequestDto dto) throws RuntimeException{
        return scheduleService.findScheduleForMonth(dto.getMemberId(), dto.getYear(), dto.getMonth())
                .map(converter::toScheduleDtoList);
    }

    public Mono<String> deleteSchedule(Long schedule_id) throws RuntimeException{
        return notificationService.deleteByScheduleId(schedule_id)
                .then(Mono.defer(() -> scheduleService.delete(schedule_id)))
                .then(Mono.defer(() -> Mono.just("delete success")));
    }

    public Mono<String> updateSchedule(ScheduleDto scheduleDto) throws RuntimeException{
        timeCalculateService = new TimeCalculateService(scheduleDto);
        return Mono.zip(scheduleService.getSchedule(scheduleDto.getScheduleId()), colorService.findColor(scheduleDto.getColorId()))
                .flatMap(req -> scheduleService.save(converter.toScheduleForUpdate(req.getT1(), req.getT2(), scheduleDto)))
                .flatMap(schedule -> notificationService.deleteByScheduleId(scheduleDto.getScheduleId()))
                .then(Mono.defer(() -> notificationService.saveAll(converter.toNotificationList(scheduleDto.getNotificationDtoList(), scheduleDto.getScheduleId(), timeCalculateService))))
                .flatMap(result -> Mono.just("update success"));
    }
}
