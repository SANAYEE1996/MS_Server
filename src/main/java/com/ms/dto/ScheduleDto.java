package com.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long calendarId;

    @NotBlank(message = "memberId must not Null")
    private Long memberId;

    @NotBlank(message = "year must not Null")
    private Integer year;

    @NotBlank(message = "month must not Null")
    private Integer month;

    @NotBlank(message = "day must not Null")
    private Integer day;

    private Long scheduleId;

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
