package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CombineServiceTest {

    @Autowired
    private CombineService combineService;

    @Test
    void saveTest(){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().build());
        notificationDtoList.add(NotificationDto.builder().build());
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(2L)
                .colorId(1L)
                .startYear(2023)
                .startMonth(5)
                .startMonth(26)
                .startHour(10)
                .startMin(0)
                .endYear(2023)
                .endMonth(5)
                .endDay(26)
                .endHour(11)
                .endMin(0)
                .title("입금")
                .note("티켓 입금")
                .notificationDtoList(notificationDtoList)
                .build();

        combineService.saveSchedule(scheduleDto);
    }

    @Test
    void getTest(){
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().memberId(1L).year(2023).month(8).build();

        List<ScheduleDto> scheduleDtoList = combineService.findScheduleForMonth(scheduleRequestDto);

        System.out.println("is Empty???? "+scheduleDtoList.isEmpty());
        for(ScheduleDto scheduleDto : scheduleDtoList){
            System.out.println("scheduleDto : " + scheduleDto.toString());
        }

    }

    @Test
    void getDayTest(){
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().scheduleId(1L).build();

        ScheduleDto scheduleDto = combineService.findScheduleForDay(scheduleRequestDto);
        List<NotificationDto> notificationDtoList = scheduleDto.getNotificationDtoList();
        System.out.println(scheduleDto.toString());
        for(NotificationDto notificationDto : notificationDtoList){
            System.out.println(notificationDto.toString());
        }

    }

    @Test
    void deleteTest(){
        Long schedule_id = 4L;

        combineService.deleteSchedule(schedule_id);
    }

    @Test
    void updateTest(){

    }
}
