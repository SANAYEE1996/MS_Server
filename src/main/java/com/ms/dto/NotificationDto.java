package com.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;

    private Long scheduleId;

    @NotBlank(message = "name must not Null")
    private String name;

    private String notificationTime;

    public void setScheduleId(Long scheduleId){
        this.scheduleId = scheduleId;
    }
}
