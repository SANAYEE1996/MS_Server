package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.Color;
import com.ms.entity.Schedule;
import com.ms.repository.NativeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CombineService {

    private final ScheduleService scheduleService;

    private final ScheduleSupportService scheduleSupportService;

    private final NotificationService notificationService;

    private final ColorService colorService;

    private final NativeRepository nativeRepository;

    private final Converter converter;

    private TimeCalculateService timeCalculateService;

    public void saveSchedule(ScheduleDto scheduleDto) throws RuntimeException, ParseException {
        ScheduleValidationCheck.getInstance(scheduleDto).check();
        Color color = colorService.findColor(scheduleDto.getColorId());
        Schedule schedule = new Schedule(color, scheduleDto);
        Long scheduleId = scheduleService.save(schedule);
        List<NotificationDto> inputNotificationList = scheduleDto.getNotificationDtoList();
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        timeCalculateService = new TimeCalculateService(scheduleDto);
        for(NotificationDto notificationDto : inputNotificationList){
            notificationDtoList.add(NotificationDto
                                    .builder()
                                    .scheduleId(scheduleId)
                                    .type(notificationDto.getType())
                                    .value(notificationDto.getValue())
                                    .notificationTime(timeCalculateService.getTime(notificationDto.getType(), notificationDto.getValue()))
                                    .build());
        }
        notificationService.saveAll(notificationDtoList);
    }

    public ScheduleDto findScheduleForDay(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        ScheduleDto scheduleDto = scheduleSupportService.findScheduleForDay(scheduleRequestDto);
        scheduleDto.setNotification(converter.toNotificationDtoList(notificationService.getNotificationList(scheduleRequestDto.getScheduleId())));
        return scheduleDto;
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{
        return scheduleSupportService.findScheduleForMonth(scheduleRequestDto);
    }

    public void deleteSchedule(Long schedule_id) throws RuntimeException{
        if(!scheduleService.isExistedSchedule(schedule_id)) throw new RuntimeException(schedule_id+" is not existed id");
        nativeRepository.deleteNotificationByScheduleId(schedule_id);
        nativeRepository.deleteScheduleById(schedule_id);
    }

    public void updateSchedule(ScheduleDto scheduleDto) throws RuntimeException, ParseException{
        ScheduleValidationCheck.getInstance(scheduleDto).check();
        List<NotificationDto> inputNotificationList = scheduleDto.getNotificationDtoList();
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        timeCalculateService = new TimeCalculateService(scheduleDto);
        for(NotificationDto notificationDto : inputNotificationList){
            notificationDtoList.add(NotificationDto
                                    .builder()
                                    .scheduleId(scheduleDto.getScheduleId())
                                    .type(notificationDto.getType())
                                    .value(notificationDto.getValue())
                                    .notificationTime(timeCalculateService.getTime(notificationDto.getType(), notificationDto.getValue()))
                                    .build());
        }
        scheduleSupportService.updateSchedule(scheduleDto);
        notificationService.deleteByScheduleId(scheduleDto.getScheduleId());
        notificationService.saveAll(notificationDtoList);
    }
}
