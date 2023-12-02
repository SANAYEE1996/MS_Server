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
    void saveTest(){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.WEEK).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.DAY).value(1).build());
        notificationDtoList.add(NotificationDto.builder().type(NotificationType.HOUR).value(6).build());

        String title = "영화괴물 보기";
        String note = "혼자 영화 봄 ㅋㅋㅋ";
        String location = "메가막스 이수점";

        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .memberId(1L)
                .colorId(2L)
                .startYear(2023)
                .startMonth(12)
                .startDay(7)
                .startHour(12)
                .startMin(40)
                .endYear(2023)
                .endMonth(12)
                .endDay(7)
                .endHour(14)
                .endMin(50)
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
        ScheduleRequestDto scheduleRequestDto = ScheduleRequestDto.builder()
                .memberId(1L)
                .year(2023)
                .month(12)
                .build();

        List<ScheduleDto> result = combineService.findScheduleForMonth(scheduleRequestDto).block();

        for(ScheduleDto dto : result){
            System.out.println(dto.getTitle() + " " +dto.getNote());
        }

    }

    @Test
    void deleteTest(){
        Long schedule_id = 2L;

        String resultText = combineService.deleteSchedule(schedule_id).block();

        assertThat(resultText).isEqualTo("delete success");
    }

    @Test
    void updateTest(){
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(NotificationDto.builder().scheduleId(15L).type(NotificationType.MIN).value(25).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(15L).type(NotificationType.MIN).value(150).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(15L).type(NotificationType.HOUR).value(3).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(15L).type(NotificationType.DAY).value(1).build());
        notificationDtoList.add(NotificationDto.builder().scheduleId(15L).type(NotificationType.WEEK).value(2).build());

        String modifyTitle = "서울역 출발";
        String modifyNote = "기차 꼭 늦지마라 !!";

        ScheduleDto scheduleDto = ScheduleDto
                .builder()
                .scheduleId(15L)
                .colorId(1L)
                .memberId(1L)
                .startYear(2023)
                .startMonth(12)
                .startDay(19)
                .startHour(18)
                .startMin(30)
                .endYear(2023)
                .endMonth(12)
                .endDay(19)
                .endHour(21)
                .endMin(30)
                .title(modifyTitle)
                .note(modifyNote)
                .notificationDtoList(notificationDtoList)
                .build();

        String resultText = combineService.updateSchedule(scheduleDto).block();

        assertThat(resultText).isEqualTo("update success");

        ScheduleDto expect = combineService.findScheduleForDay(ScheduleRequestDto.builder().scheduleId(15L).build()).block();


        assertThat(modifyTitle).isEqualTo(expect.getTitle());
        assertThat(modifyNote).isEqualTo(expect.getNote());
    }
}
