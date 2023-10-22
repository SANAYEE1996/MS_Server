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
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().scheduleId(2L).type(NotificationType.MIN).value(3).notificationTime("2023-10-25 18:57").build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(2L).type(NotificationType.HOUR).value(1).notificationTime("2023-10-25 18:00").build());
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .scheduleId(2L)
                .colorId(1L)
                .startYear(2023)
                .startMonth(10)
                .startDay(25)
                .startHour(19)
                .startMin(00)
                .endYear(2023)
                .endMonth(10)
                .endDay(25)
                .endHour(21)
                .endMin(10)
                .title("세미나")
                .note("자바 세미나")
                .notificationDtoList(notificationDtoList)
                .build();

        combineService.updateSchedule(scheduleDto);

        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().scheduleId(2L).build();

        ScheduleDto respectSchedule = combineService.findScheduleForDay(scheduleRequestDto);

        assertThat(scheduleDto.getTitle()).isEqualTo(respectSchedule.getTitle());
        assertThat(scheduleDto.getNote()).isEqualTo(respectSchedule.getNote());
    }
}
