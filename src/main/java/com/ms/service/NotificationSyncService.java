package com.ms.service;

import com.ms.dto.NotificationServerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component("notificationSynchronizeService")
public class NotificationSyncService {

    @Value("${url.notification}")
    private String notificationUrl;

    public Mono<ServerResponse> send(List<NotificationServerDto> list){
        WebClient webClient = WebClient.builder()
                .baseUrl(notificationUrl)
                .build();

        return webClient.post().uri("/notification/save").bodyValue(list).retrieve()
                .onStatus(HttpStatusCode::is3xxRedirection, response -> Mono.error(new RuntimeException("server 3XX error")))
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("UnAuthorized")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("server 5XX error")))
                .bodyToMono(String.class)
                .flatMap(req -> ServerResponse.ok().bodyValue(req));
    }
}
