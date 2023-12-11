package com.ms.reactive;

import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import com.ms.service.CombineService;
import com.ms.service.NotificationSyncService;
import com.ms.service.ScheduleValidationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Component("scheduleHandler")
@RequiredArgsConstructor
public class ScheduleHandler {

    private final AuthFilter filter;

    private final CombineService combineService;

    private final NotificationSyncService notificationSyncService;

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
        return filter.authenticate(request)
                .flatMap(req -> Mono.zip(
                            Mono.just(req),
                            request.bodyToMono(ScheduleDto.class)
                                    .doOnNext(result -> ScheduleValidationCheck.getInstance(result).check())
                        )
                )
                .flatMap(req -> Mono.zip(
                            Mono.just(req.getT1()),
                            combineService.saveSchedule(req.getT2())
                        )
                )
                .flatMap(req -> Mono.zip(
                            Mono.just(req.getT1()),
                            Mono.just(req.getT2()),
                            combineService.findNotificationListByScheduleId(req.getT2().getId())
                        )
                )
                .map(req -> combineService.toNotificationServerDtoList(req.getT2(), req.getT1(), req.getT3()))
                .flatMap(notificationSyncService::save)
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> deleteSchedule(ServerRequest request){
        return filter.authenticate(request)
                .flatMap(req -> Mono.zip(
                                Mono.just(req),
                                request.bodyToMono(ScheduleRequestDto.class)
                                        .doOnNext(result -> checkScheduleIdNull(result.getScheduleId()))
                        )
                )
                .flatMap(req -> Mono.zip(
                                Mono.just(req.getT1()),
                                Mono.just(req.getT2()),
                                combineService.deleteSchedule(req.getT2().getScheduleId())
                        )
                )
                .flatMap(req -> notificationSyncService.delete(req.getT2().getScheduleId()))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> updateSchedule(ServerRequest request){
        return filter.authenticate(request)
                .flatMap(req -> Mono.zip(
                                Mono.just(req),
                                request.bodyToMono(ScheduleDto.class)
                                        .doOnNext(result -> checkScheduleIdNull(result.getScheduleId()))
                                        .doOnNext(result -> ScheduleValidationCheck.getInstance(result).check())
                        )
                )
                .flatMap(req -> Mono.zip(
                                Mono.just(req.getT1()),
                                combineService.updateSchedule(req.getT2())
                        )
                )
                .flatMap(req -> Mono.zip(
                                Mono.just(req.getT1()),
                                Mono.just(req.getT2()),
                                combineService.findNotificationListByScheduleId(req.getT2().getId())
                        )
                )
                .flatMap(req -> Mono.zip(
                        Mono.just(combineService.toNotificationServerDtoList(req.getT2(), req.getT1(), req.getT3())),
                        Mono.just(req.getT2().getId())
                ))
                .flatMap(req -> notificationSyncService.update(req.getT2(), req.getT1()))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(new ErrorResponse(HttpStatus.BAD_REQUEST, error.getMessage())));
    }

    public Mono<ServerResponse> check(ServerRequest request){
        return filter.authenticate(request)
                .flatMap(req -> ServerResponse.ok().bodyValue(req))
                .onErrorResume(req -> ServerResponse.badRequest().bodyValue(req.getMessage()));
    }

    private void checkScheduleIdNull(Long id){
        if(id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "schedule id must not be null");
    }
}
