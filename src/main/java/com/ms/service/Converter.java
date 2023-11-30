package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.dto.ScheduleDto;
import com.ms.entity.Notification;
import com.ms.entity.Schedule;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class Converter {

    public Schedule toSchedule(ScheduleDto scheduleDto, Long colorId){
        return new Schedule(scheduleDto.getScheduleId()
                            ,colorId
                            ,scheduleDto.getMemberId()
                            ,scheduleDto.getStartYear()
                            ,scheduleDto.getStartMonth()
                            ,scheduleDto.getStartDay()
                            ,scheduleDto.getStartHour()
                            ,scheduleDto.getStartMin()
                            ,scheduleDto.getEndYear()
                            ,scheduleDto.getEndMonth()
                            ,scheduleDto.getEndDay()
                            ,scheduleDto.getEndHour()
                            ,scheduleDto.getEndMin()
                            ,scheduleDto.getLocation()
                            ,scheduleDto.getTitle()
                            ,scheduleDto.getNote());
    }

    public Schedule toScheduleForUpdate(Schedule schedule, ScheduleDto scheduleDto){
        return new Schedule(schedule.getId()
                ,scheduleDto.getColorId()
                ,schedule.getMemberId()
                ,scheduleDto.getStartYear()
                ,scheduleDto.getStartMonth()
                ,scheduleDto.getStartDay()
                ,scheduleDto.getStartHour()
                ,scheduleDto.getStartMin()
                ,scheduleDto.getEndYear()
                ,scheduleDto.getEndMonth()
                ,scheduleDto.getEndDay()
                ,scheduleDto.getEndHour()
                ,scheduleDto.getEndMin()
                ,scheduleDto.getLocation()
                ,scheduleDto.getTitle()
                ,scheduleDto.getNote());
    }

    public List<Notification> toNotificationList(List<NotificationDto> dtoList, Long scheduleId, TimeCalculateService calculate){
        return dtoList.stream().map(s -> {
            try {
                return toNotification(s, scheduleId, calculate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private Notification toNotification(NotificationDto dto, Long scheduleId, TimeCalculateService calculate) throws ParseException {
        return new Notification(0L, scheduleId, dto.getType(), dto.getValue(), calculate.getTime(dto.getType(), dto.getValue()));
    }

    public List<NotificationDto> toNotificationDtoList(List<Notification> notificationList){
        return notificationList.stream().map(this::toNotificationDto).toList();
    }

    private NotificationDto toNotificationDto(Notification notification){
        return new NotificationDto(notification.getId(), notification.getScheduleId(), notification.getNotificationType(), notification.getValue(), notification.getTime());
    }
}
