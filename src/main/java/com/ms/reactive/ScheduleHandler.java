package com.ms.reactive;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.service.CombineService;
import com.ms.service.ScheduleValidationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component("scheduleHandler")
@RequiredArgsConstructor
public class ScheduleHandler {

    private final CombineService combineService;

    public Mono<ServerResponse> getDaySchedule(ServerRequest request){
        return request.bodyToMono(ScheduleRequestDto.class)
                .flatMap(combineService::findScheduleForDay)
                .flatMap(scheduleDay -> ServerResponse.ok().bodyValue(scheduleDay))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> getMonthSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleRequestDto.class)
                .flatMap(combineService::findScheduleForMonth)
                .flatMap(scheduleMonth -> ServerResponse.ok().bodyValue(scheduleMonth))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> saveSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleDto.class)
                .doOnNext(req -> ScheduleValidationCheck.getInstance(req).check())
                .flatMap(combineService::saveSchedule)
                .flatMap(resultComment -> ServerResponse.ok().bodyValue(resultComment))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> deleteSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleRequestDto.class)
                .flatMap(req -> combineService.deleteSchedule(req.getScheduleId()))
                .flatMap(resultComment -> ServerResponse.ok().bodyValue(resultComment))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> updateSchedule(ServerRequest request){
        return request.bodyToMono(ScheduleDto.class)
                .doOnNext(req -> {
                    if(req.getScheduleId() == null){
                        throw new RuntimeException("schedule id must not null or 0");
                    }
                })
                .doOnNext(req -> ScheduleValidationCheck.getInstance(req).check())
                .flatMap(combineService::updateSchedule)
                .flatMap(resultComment -> ServerResponse.ok().bodyValue(resultComment))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }
}
