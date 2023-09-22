package com.ms.dto;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "memberId must not Null")
    private Long memberId;

    @NotNull(message = "memberId must not Null")
    private Long colorId;

    @Max(2100)
    @Min(1900)
    @NotNull(message = "start Year must not Null")
    private Integer startYear;

    @Max(12)
    @Min(1)
    @NotNull(message = "start Month must not Null")
    private Integer startMonth;

    @Max(31)
    @Min(1)
    @NotNull(message = "start Day must not Null")
    private Integer startDay;

    @Max(23)
    @Min(0)
    @NotNull(message = "startHour must not Null")
    private Integer startHour;

    @Max(59)
    @Min(0)
    @NotNull(message = "startMin must not Null")
    private Integer startMin;

    @Max(2100)
    @Min(1900)
    @NotNull(message = "endYear must not Null")
    private Integer endYear;

    @Max(12)
    @Min(1)
    @NotNull(message = "endMonth must not Null")
    private Integer endMonth;

    @Max(31)
    @Min(1)
    @NotNull(message = "end Day must not Null")
    private Integer endDay;

    @Max(23)
    @Min(0)
    @NotNull(message = "endHour must not Null")
    private Integer endHour;

    @Max(59)
    @Min(0)
    @NotNull(message = "endMin must not Null")
    private Integer endMin;

    @NotBlank(message = "title must not Null")
    private String title;

    @NotBlank(message = "note must not Null")
    private String note;
}
