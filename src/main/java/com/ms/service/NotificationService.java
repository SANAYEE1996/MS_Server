package com.ms.service;

import com.ms.entity.Notification;
import com.ms.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Mono<String> saveAll(List<Notification> notificationList){
        return notificationRepository.saveAll(notificationList).collectList()
                .flatMap(s -> Mono.just("notification save success"));
    }

    public Mono<List<Notification>> getNotificationList(Long scheduleId){
        return notificationRepository.findByScheduleId(scheduleId).collectList();
    }

    public Mono<Void> deleteByScheduleId(Long scheduleId){
        return notificationRepository.deleteByScheduleId(scheduleId);
    }
}
