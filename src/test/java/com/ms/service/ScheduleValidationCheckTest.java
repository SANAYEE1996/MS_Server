package com.ms.service;

import com.ms.dto.ScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScheduleValidationCheckTest {

    @Test
    void dateCheck(){
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .startDay(1996)
                .startMonth(2)
                .startDay(30)
                .startHour(12)
                .startMin(59)
                .endYear(1997)
                .endMonth(12)
                .endDay(28)
                .endHour(12)
                .endMin(55)
                .build();
        ScheduleValidationCheck.getInstance(scheduleDto).check();
    }
}
