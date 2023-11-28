package com.ms.service;

import com.ms.dto.NotificationDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.entity.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CombineServiceTest {

    @Autowired
    private CombineService combineService;

    @Test
    void saveTest() throws ParseException {
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.WEEK).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.DAY).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.HOUR).value(6).build());
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(1L)
                .colorId(3L)
                .startYear(2023)
                .startMonth(11)
                .startDay(8)
                .startHour(0)
                .startMin(0)
                .endYear(2023)
                .endMonth(11)
                .endDay(9)
                .endHour(0)
                .endMin(0)
                .title("어머님 생신")
                .note("매번 감사합니다.")
                .location("한우집")
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
    void updateTest() throws ParseException{
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().scheduleId(8L).type(NotificationType.MIN).value(25).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(8L).type(NotificationType.MIN).value(150).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(8L).type(NotificationType.HOUR).value(3).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(8L).type(NotificationType.DAY).value(1).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(8L).type(NotificationType.WEEK).value(2).build());
        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .scheduleId(8L)
                .colorId(2L)
                .startYear(2023)
                .startMonth(11)
                .startDay(8)
                .startHour(18)
                .startMin(30)
                .endYear(2023)
                .endMonth(11)
                .endDay(8)
                .endHour(21)
                .endMin(30)
                .title("생신 파티")
                .note("어머니 생신 파티")
                .notificationDtoList(notificationDtoList)
                .build();

        combineService.updateSchedule(scheduleDto);

        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().scheduleId(8L).build();

        ScheduleDto respectSchedule = combineService.findScheduleForDay(scheduleRequestDto);

        assertThat(scheduleDto.getTitle()).isEqualTo(respectSchedule.getTitle());
        assertThat(scheduleDto.getNote()).isEqualTo(respectSchedule.getNote());
    }
}
