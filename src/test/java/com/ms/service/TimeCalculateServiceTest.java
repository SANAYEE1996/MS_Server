package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.entity.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TimeCalculateServiceTest {

    private ScheduleDto scheduleDto;
    private TimeCalculateService timeCalculateService;

    @BeforeEach
    void init(){
        scheduleDto = ScheduleDto.builder()
                .startYear(2023)
                .startMonth(10)
                .startDay(23)
                .startHour(19)
                .startMin(0).build();

        timeCalculateService = new TimeCalculateService(scheduleDto);
    }

    @Test
    @DisplayName("분전 체크")
    void minCalculateTest() throws ParseException {
        String calTime = timeCalculateService.getTime(NotificationType.MIN, 7);
        String respectTime = "202310231853";
        assertThat(calTime).isEqualTo(respectTime);
    }

    @Test
    @DisplayName("시간전 체크")
    void hourCalculateTest() throws ParseException{
        String calTime = timeCalculateService.getTime(NotificationType.HOUR, 14);
        String respectTime = "202310230500";
        assertThat(calTime).isEqualTo(respectTime);
    }

    @Test
    @DisplayName("일전 체크")
    void dayCalculateTest() throws ParseException{
        String calTime = timeCalculateService.getTime(NotificationType.DAY, 28);
        String respectTime = "202309251900";
        assertThat(calTime).isEqualTo(respectTime);
    }

    @Test
    @DisplayName("주전 체크")
    void weekCalculateTest() throws ParseException{
        String calTime = timeCalculateService.getTime(NotificationType.WEEK, 12);
        String respectTime = "202307311900";
        assertThat(calTime).isEqualTo(respectTime);
    }
}
