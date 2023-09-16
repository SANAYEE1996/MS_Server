package com.ms.service;

import com.ms.dto.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidationCheck {

    public void checkDate(ScheduleDto calendarDto) throws RuntimeException{
        int year = calendarDto.getYear();
        int month = calendarDto.getMonth();
        int day = calendarDto.getDay();
        boolean leapYear = isLeapYear(year);
        if((month == 4 || month == 6 || month == 9 || month == 11 ) && day > 30){
            throw new RuntimeException(month + " must under 30 days");
        }
        if(month == 2){
            if(leapYear && day != 29){
                throw new RuntimeException(day+" "+month+" must 29 days");
            }
            if(!leapYear && day != 28){
                throw new RuntimeException(day+" "+month+" must 28 days");
            }
        }
    }

    public void checkTime(ScheduleDto timeDto) throws RuntimeException{
        int startHour = timeDto.getStartHour();
        int startMin = timeDto.getStartMin();
        int endHour = timeDto.getEndHour();
        int endMin = timeDto.getEndMin();
        if(!(startHour <= endHour && startMin <= endMin)){
            throw new RuntimeException("start time must smaller than end time");
        }
    }

    private boolean isLeapYear(int year){
        return year % 4 == 0 && year % 100 != 0 && year % 400 == 0;
    }
}
