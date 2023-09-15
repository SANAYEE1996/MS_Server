package com.ms.controller;

import com.ms.dto.ResponseBody;
import com.ms.dto.ResponseDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.service.*;
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
@RequestMapping("/schedule")
public class ScheduleController {

    private final CombineService combineService;

    @PostMapping(value = "/save")
    public ResponseDto save(@RequestBody @Valid ScheduleDto scheduleDto){
        try {
            combineService.saveSchedule(scheduleDto);
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(404).message("save fail").build();
        }
        return ResponseDto.builder().code(200).message("save success").build();
    }

    @PostMapping(value = "/month")
    public ResponseDto findMonth(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto){
        try{
            return ResponseDto.builder().code(200).message("find success").body(new ResponseBody<>(combineService.findScheduleForMonth(scheduleRequestDto))).build();
        }catch(RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(404).message("fail").build();
        }
    }
}