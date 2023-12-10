package com.ms.service;

import com.ms.dto.NotifyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component("notificationSynchronizeService")
public class NotificationSyncService {

    @Value("${url.notification}")
    private String notificationUrl;

    public Mono<ServerResponse> save(List<NotifyDto> list){
        return send(WebClient.builder().baseUrl(notificationUrl).build()
                .post().uri("/notification/save").bodyValue(new SaveDto(list)).retrieve());
    }

    public Mono<ServerResponse> update(Long scheduleId, List<NotifyDto> list){
        return send(WebClient.builder().baseUrl(notificationUrl).build()
                .post().uri("/notification/update").bodyValue(new UpdateDto(scheduleId, list)).retrieve());
    }

    public Mono<ServerResponse> delete(Long scheduleId){
        return send(WebClient.builder().baseUrl(notificationUrl).build()
                .get().uri("/notification/delete/{id}", scheduleId).retrieve());
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SaveDto{
        private List<NotifyDto> dtoList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UpdateDto{
        private Long scheduleId;
        private List<NotifyDto> dtoList;
    }

    private Mono<ServerResponse> send(ResponseSpec responseSpec){
        return responseSpec
                .onStatus(HttpStatusCode::is3xxRedirection, response -> Mono.error(new RuntimeException("server 3XX error")))
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("UnAuthorized")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("server 5XX error")))
                .bodyToMono(String.class)
                .flatMap(req -> ServerResponse.ok().bodyValue(req));
    }
}
