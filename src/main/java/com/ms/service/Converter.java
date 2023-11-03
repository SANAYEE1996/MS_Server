package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    public List<NotificationDto> toNotificationDtoList(List<Notification> notificationList){
        return notificationList.stream().map(this::toNotificationDto).collect(Collectors.toList());
    }

    private NotificationDto toNotificationDto(Notification notification){
        return new NotificationDto(notification.getId(), notification.getSchedule().getId(), notification.getNotificationType(), notification.getValue(), notification.getTime());
    }
}
