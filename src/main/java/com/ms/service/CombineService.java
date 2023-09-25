package com.ms.service;

import com.ms.dto.NotificationDto;
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

    private final NotificationService notificationService;

    private final ColorService colorService;

    private final Converter converter;

    public void saveSchedule(ScheduleDto scheduleDto) throws RuntimeException{
        ScheduleValidationCheck.getInstance(scheduleDto).check();
        Color color = colorService.findColor(scheduleDto.getColorId());
        Schedule schedule = new Schedule(color, scheduleDto);
        Long scheduleId = scheduleService.save(schedule);
        List<NotificationDto> notificationDtoList = scheduleDto.getNotificationDtoList();
        for(NotificationDto notificationDto : notificationDtoList){
            notificationDto.setScheduleId(scheduleId);
        }
        notificationService.saveAll(notificationDtoList);
    }

    public ScheduleDto findScheduleForDay(ScheduleRequestDto scheduleRequestDto)throws RuntimeException{
        List<NotificationDto> notificationDtoList = converter.toNotificationDtoList(notificationService.getNotificationList(scheduleRequestDto.getScheduleId()));

        return new ScheduleDto();
    }

    public List<ScheduleDto> findScheduleForMonth(ScheduleRequestDto scheduleRequestDto) throws RuntimeException{

        return new ArrayList<>();
    }

    public void deleteSchedule(Long schedule_id){

    }
}
