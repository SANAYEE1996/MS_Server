package com.ms.dto;

import com.ms.entity.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;

    private Long scheduleId;

    @NotNull(message = "유효하지 않은 type 값")
    private NotificationType type;

    @NotNull(message = "value must not Null")
    private Integer value;

    private String notificationTime;
}
