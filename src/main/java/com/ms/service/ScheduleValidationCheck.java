package com.ms.service;

import com.ms.dto.ScheduleDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ScheduleValidationCheck {

    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMin;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endMin;

    public ScheduleValidationCheck(ScheduleDto timeDto){
        this.startYear = timeDto.getStartYear();
        this.startMonth = timeDto.getStartMonth();
        this.startDay = timeDto.getStartDay();
        this.startHour = timeDto.getStartHour();
        this.startMin = timeDto.getStartMin();
        this.endYear = timeDto.getEndYear();
        this.endMonth = timeDto.getEndMonth();
        this.endDay = timeDto.getEndDay();
        this.endHour = timeDto.getEndHour();
        this.endMin = timeDto.getEndMin();
    }

    public static ScheduleValidationCheck getInstance(ScheduleDto timeDto){
        return new ScheduleValidationCheck(timeDto);
    }

    public void check() throws RuntimeException{
        checkDate();
        checkTime();
    }

    private void checkDate() throws RuntimeException{
        checkDate(startMonth, startDay, isLeapYear(startYear));
        checkDate(endMonth, endDay, isLeapYear(endYear));
    }

    private void checkDate(int month, int day, boolean leapYear) throws RuntimeException{
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

    private void checkTime() throws RuntimeException{
        if(startYear < endYear){
            return;
        }
        if(startYear > endYear){
            throw new RuntimeException("startYear must smaller than endYear");
        }
        if(startMonth < endMonth){
            return;
        }
        if(startMonth > endMonth){
            throw new RuntimeException("startMonth must smaller than endMonth");
        }
        if(startDay < endDay){
            return;
        }
        if(startDay > endDay){
            throw new RuntimeException("startDay must smaller than endDay");
        }
        if(!(startHour <= endHour && startMin <= endMin)){
            throw new RuntimeException("start time must smaller than end time");
        }
    }

    private boolean isLeapYear(int year){
        return year % 4 == 0 && year % 100 != 0 && year % 400 == 0;
    }
}
