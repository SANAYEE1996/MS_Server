package com.ms.service;

import com.ms.entity.Notification;
import com.ms.entity.Schedule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("notificationSynchronizeService")
public class NotificationSyncService {

    @Value("${url.notification}")
    private String url;

    public void send(Schedule schedule, Long memberId, List<Notification> notificationList){

    }
}
