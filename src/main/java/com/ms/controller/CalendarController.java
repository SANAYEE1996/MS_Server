package com.ms.controller;

import com.ms.dto.CalendarDto;
import com.ms.dto.ResponseDto;
import com.ms.service.CalendarService;
import com.ms.service.Converter;
import com.ms.service.ValidationCheck;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    private final ValidationCheck validationCheck;

    private final Converter converter;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody @Valid CalendarDto calendarDto){
        try {
            validationCheck.checkDate(calendarDto);
            calendarService.save(converter.toCalendar(calendarDto));
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(404).message("save fail").build();
        }
        return ResponseDto.builder().code(200).message("save success").build();
    }
}
