package com.ms.service;

import org.springframework.beans.factory.annotation.Value;

public class notificationSyncService {

    @Value("${notification.url}")
    private String url;
}
