package com.ms.service;

import com.ms.dto.ScheduleDto;
import com.ms.entity.NotificationType;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
@NoArgsConstructor
public class TimeCalculateService {

    private String startTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

    public TimeCalculateService(ScheduleDto scheduleDto){
        StringBuilder sb = new StringBuilder();
        sb.append(scheduleDto.getStartYear());
        sb.append(scheduleDto.getStartMonth());
        sb.append(scheduleDto.getStartDay());
        sb.append(scheduleDto.getStartHour());
        sb.append(scheduleDto.getStartMin());
        startTime = sb.toString();
    }

    public String getTime(NotificationType type, int value) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startTime));
        switch (type){
            case WEEK -> cal.add(Calendar.DATE, -value*7);
            case DAY -> cal.add(Calendar.DATE, -value);
            case HOUR -> cal.add(Calendar.HOUR, -value);
            case MIN -> cal.add(Calendar.MINUTE, -value);
        }
        return sdf.format(cal.getTime());
    }
}
