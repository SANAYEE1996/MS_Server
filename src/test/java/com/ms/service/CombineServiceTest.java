package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CombineServiceTest {

    @Autowired
    private CombineService combineService;

    @Test
    void saveTest(){
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(2L)
                .year(2023)
                .month(5)
                .day(26)
                .startHour(10)
                .startMin(0)
                .endHour(11)
                .endMin(00)
                .title("입금")
                .note("티켓 입금")
                .build();

        combineService.saveSchedule(scheduleDto);
    }

    @Test
    void getTest(){
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().memberId(2L).year(2023).month(4).build();

        List<ScheduleDto> scheduleDtoList = combineService.findScheduleForMonth(scheduleRequestDto);

        System.out.println("is Empty???? "+scheduleDtoList.isEmpty());
        for(ScheduleDto scheduleDto : scheduleDtoList){
            System.out.println(scheduleDto.getCalendarId()+" "+ scheduleDto.getMemberId()+" "+scheduleDto.getTitle() + " " +scheduleDto.getNote());
        }

    }

    @Test
    void deleteTest(){
        Long schedule_id = 4L;

        combineService.deleteSchedule(schedule_id);
    }
}
