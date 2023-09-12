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
                .startHour(18)
                .startMin(0)
                .endHour(23)
                .endMin(59)
                .title("party")
                .note("맛있는거 먹기로 함.")
                .build();

        combineService.saveSchedule(scheduleDto);
    }
}
