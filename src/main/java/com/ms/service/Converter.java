package com.ms.service;

import com.ms.dto.MemberInfoDto;
import com.ms.dto.NotificationDto;
import com.ms.dto.NotificationServerDto;
import com.ms.dto.ScheduleDto;
import com.ms.entity.Color;
import com.ms.entity.Notification;
import com.ms.entity.Schedule;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class Converter {

    public Schedule toSchedule(ScheduleDto scheduleDto, Color color){
        return new Schedule(scheduleDto.getScheduleId()
                ,color.getId()
                ,color.getName()
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

    public Schedule toScheduleForUpdate(Schedule schedule, Color color, ScheduleDto scheduleDto){
        return new Schedule(schedule.getId()
                ,color.getId()
                ,color.getName()
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

    public List<ScheduleDto> toScheduleDtoList(List<Schedule> list){
        return list.stream().map(this::toScheduleDto).toList();
    }

    public ScheduleDto toScheduleDto(Schedule schedule){
        return new ScheduleDto(schedule.getId(),
                schedule.getMemberId(),
                schedule.getColorId(),
                schedule.getColorName(),
                schedule.getStartYear(),
                schedule.getStartMonth(),
                schedule.getStartDay(),
                schedule.getStartHour(),
                schedule.getStartMin(),
                schedule.getEndYear(),
                schedule.getEndMonth(),
                schedule.getEndDay(),
                schedule.getEndHour(),
                schedule.getEndMin(),
                schedule.getLocation(),
                schedule.getTitle(),
                schedule.getNote());
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
        return new Notification(null, scheduleId, dto.getType(), dto.getValue(), calculate.getTime(dto.getType(), dto.getValue()));
    }

    public List<NotificationDto> toNotificationDtoList(List<Notification> notificationList){
        return notificationList.stream().map(this::toNotificationDto).toList();
    }

    private NotificationDto toNotificationDto(Notification notification){
        return new NotificationDto(notification.getId(), notification.getScheduleId(), notification.getNotificationType(), notification.getValue(), notification.getNotificationTime());
    }

    public List<NotificationServerDto> toNotificationServerDtoList(Schedule schedule, MemberInfoDto dto, List<Notification> list){
        return list.stream().map(s -> toNotificationServerDto(schedule, dto, s)).toList();
    }

    private NotificationServerDto toNotificationServerDto(Schedule schedule, MemberInfoDto dto, Notification notification){
        return new NotificationServerDto(schedule.getId(), dto.getId(), dto.getEmail(), schedule.getTitle(), notification.getNotificationTime());
    }
}
