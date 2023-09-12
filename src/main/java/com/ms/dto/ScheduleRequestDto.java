package com.ms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {

    @NotNull(message = "memberId must not Null")
    private Long memberId;

    @NotNull(message = "memberId must not Null")
    private Integer year;

    @NotNull(message = "memberId must not Null")
    private Integer month;

    private Integer day;
}
