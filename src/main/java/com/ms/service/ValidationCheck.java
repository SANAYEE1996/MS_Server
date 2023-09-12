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
        if( !(1900 <= year && year <= 2100) || !(1 <= month && month <= 12) || day <= 0){
            throw new RuntimeException("year or month or day limit exceeded");
        }
        boolean leapYear = isLeapYear(year);
        if((month == 1 || month == 3 || month == 5 || month == 7 ||
           month == 8 || month == 10 || month == 12) && day > 31){
            throw new RuntimeException(month + " must under 31 days");
        }
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

    private boolean isLeapYear(int year){
        return year % 4 == 0 && year % 100 != 0 && year % 400 == 0;
    }
}
