package com.ms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Max(2100)
    @Min(1900)
    @NotBlank(message = "year must not Null")
    private Integer year;

    @Max(12)
    @Min(1)
    @NotBlank(message = "month must not Null")
    private Integer month;

    @Max(31)
    @Min(1)
    @NotBlank(message = "day must not Null")
    private Integer day;

    private Long scheduleId;

    @Max(23)
    @Min(0)
    @NotBlank(message = "startHour must not Null")
    private Integer startHour;

    @Max(59)
    @Min(0)
    @NotBlank(message = "startMin must not Null")
    private Integer startMin;

    @Max(23)
    @Min(0)
    @NotBlank(message = "endHour must not Null")
    private Integer endHour;

    @Max(59)
    @Min(0)
    @NotBlank(message = "endMin must not Null")
    private Integer endMin;

    @NotBlank(message = "title must not Null")
    private String title;

    @NotBlank(message = "note must not Null")
    private String note;
}
