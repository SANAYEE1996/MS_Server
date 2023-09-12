package com.ms.service;

import com.ms.dto.ScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CombineServiceTest {

    @Autowired
    private CombineService combineService;

    @Test
    void saveTest(){
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(1L)
                .year(2023)
                .month(8)
                .day(26)
                .startHour(0)
                .startMin(0)
                .endHour(23)
                .endMin(59)
                .title("내 생일")
                .note("헤헤 내 생일")
                .build();

        combineService.saveSchedule(scheduleDto);
    }
}
