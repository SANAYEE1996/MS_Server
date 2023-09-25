package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public List<NotificationDto> toNotificationDtoList(List<Notification> notificationList){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for(Notification notification : notificationList){
            notificationDtoList.add(toNotificationDto(notification));
        }
        return notificationDtoList;
    }

    private NotificationDto toNotificationDto(Notification notification){
        return new NotificationDto(notification.getId(), notification.getSchedule().getId(), notification.getName(), notification.getTime());
    }
}
