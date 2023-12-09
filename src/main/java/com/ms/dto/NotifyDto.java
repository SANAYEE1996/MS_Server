package com.ms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotifyDto {

    private Long schedule_id;

    private Long member_id;

    private String member_email;

    private String title;

    private String time;
}
