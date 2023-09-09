package com.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateDto {

    @NotBlank(message = "startHour must not Null")
    private Integer startHour;

    @NotBlank(message = "startMin must not Null")
    private Integer startMin;

    @NotBlank(message = "endHour must not Null")
    private Integer endHour;

    @NotBlank(message = "endMin must not Null")
    private Integer endMin;

    @NotBlank(message = "title must not Null")
    private String title;

    @NotBlank(message = "note must not Null")
    private String note;
}
