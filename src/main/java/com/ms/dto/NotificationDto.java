package com.ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDto {

    private Long id;

    private Long scheduleId;

    private String name;

    private String notificationTime;
}
