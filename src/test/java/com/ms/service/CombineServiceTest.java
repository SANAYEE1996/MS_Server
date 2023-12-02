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

@SpringBootTest
public class CombineServiceTest {

    @Autowired
    private CombineService combineService;

    @Test
    void saveTest(){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.WEEK).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.DAY).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.HOUR).value(6).build());

        String title = "모니터링 세미나";
        String note = "홍대입구역 한빛빌딩 2층";
        String location = "홍대입구역 근처";

        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(1L)
                .colorId(3L)
                .startYear(2023)
                .startMonth(12)
                .startDay(7)
                .startHour(19)
                .startMin(0)
                .endYear(2023)
                .endMonth(12)
                .endDay(7)
                .endHour(20)
                .endMin(0)
                .title(title)
                .note(note)
                .location(location)
                .notificationDtoList(notificationDtoList)
                .build();

        String resultText = combineService.saveSchedule(scheduleDto).block();

        System.out.println("save Test gogo");
        System.out.println(resultText);
    }

    @Test
    void getDayTest(){
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().scheduleId(8L).build();
        ScheduleDto result = combineService.findScheduleForDay(scheduleRequestDto).block();
        List<NotificationDto> list = result.getNotificationDtoList();
        System.out.println(result.getTitle());
        for(NotificationDto dto : list){
            System.out.println(dto.getNotificationTime());
        }
    }

    @Test
    void getMonthTest(){
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder().scheduleId(1L).build();

    }

    @Test
    void deleteTest(){
        Long schedule_id = 4L;

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

    }
}
