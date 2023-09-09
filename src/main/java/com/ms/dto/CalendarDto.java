package com.ms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDto {

    @NotBlank(message = "memberId must not Null")
    private Long memberId;

    @NotBlank(message = "year must not Null")
    private Integer year;

    @NotBlank(message = "month must not Null")
    private Integer month;

    @NotBlank(message = "day must not Null")
    private Integer day;
}
