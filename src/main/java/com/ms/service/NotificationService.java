package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.entity.Notification;
import com.ms.repository.NotificationBulkRepository;
import com.ms.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationBulkRepository notificationBulkRepository;

    public void saveAll(List<NotificationDto> notificationDtoList){
        notificationBulkRepository.saveAll(notificationDtoList);
    }

    public List<Notification> getNotificationList(Long scheduleId){
        return notificationRepository.findAllByMyWay(scheduleId);
    }

    public void deleteByScheduleId(Long scheduleId){
        notificationRepository.deleteForUpdateByScheduleId(scheduleId);
    }
}
