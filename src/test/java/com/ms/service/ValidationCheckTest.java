package com.ms.service;

import com.ms.dto.ScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidationCheckTest {

    @Autowired
    private ValidationCheck validationCheck;

    @Test
    void dateCheck(){
        ScheduleDto scheduleDto = ScheduleDto.builder().year(1996).month(2).day(30).build();
        validationCheck.checkDate(scheduleDto);
    }

    @Test
    void timeCheck(){
        ScheduleDto scheduleDto = ScheduleDto.builder().startHour(12).startMin(59).endHour(12).endMin(55).build();
        validationCheck.checkTime(scheduleDto);
    }
}
