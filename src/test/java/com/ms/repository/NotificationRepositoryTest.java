package com.ms.repository;

import com.ms.dto.NotificationDto;
import com.ms.entity.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class NotificationRepositoryTest {

    @Test
    @DisplayName("enum type 인데 db에 잘 저장되는지...")
    void saveTest(){
        List<NotificationDto> notificationList = new ArrayList<>();
        notificationList.add(NotificationDto.builder()
                .scheduleId(1L)
                .type(NotificationType.MIN)
                .value(7)
                .notificationTime("2023-08-26 17:53")
                .build());
        notificationList.add(NotificationDto.builder()
                .scheduleId(1L)
                .type(NotificationType.HOUR)
                .value(3)
                .notificationTime("2023-08-26 15:00")
                .build());
        notificationList.add(NotificationDto.builder()
                .scheduleId(1L)
                .type(NotificationType.DAY)
                .value(1)
                .notificationTime("2023-08-25 18:00")
                .build());
        notificationList.add(NotificationDto.builder()
                .scheduleId(1L)
                .type(NotificationType.WEEK)
                .value(2)
                .notificationTime("2023-08-12 18:00")
                .build());

    }
}
