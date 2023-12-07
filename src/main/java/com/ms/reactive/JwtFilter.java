package com.ms.reactive;

import com.ms.dto.MemberInfoDto;
import com.ms.dto.ScheduleDto;
import com.ms.dto.ScheduleRequestDto;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Value("${url.auth}")
    private String authUrl;

    @Override
    public @NotNull Mono<ServerResponse> filter(@NotNull ServerRequest request, @NotNull HandlerFunction<ServerResponse> next){
        List<String> list = request.headers().header("Authorization");
        if(list.isEmpty()){
            log.error("Jwt Token is not exist !");
            return ServerResponse.badRequest().bodyValue("Jwt Token is not exist !");
        }
        String token = list.get(0);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer")) {
            log.error("bearerToken is not exist !");
            return ServerResponse.badRequest().bodyValue("bearerToken is not exist !");
        }
        String bearerToken = token.substring(7);
        log.info("bearerToken : {}", bearerToken);
        WebClient webClient = WebClient.builder()
                .baseUrl(authUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(bearerToken))
                .build();

        return getMemberId(request)
                .flatMap(req -> webClient.get().uri("/auth/info/{req}", req).retrieve()
                        .onStatus(HttpStatusCode::is3xxRedirection, response -> Mono.error(new RuntimeException("server 3XX error")))
                        .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            log.error("4XX error :: Jwt token is not Valid");
                            return Mono.error(new RuntimeException("Jwt token is not Valid"));
                        })
                        .onStatus(HttpStatusCode::is5xxServerError, response -> {
                            log.error("auth server error 5XX");
                            return Mono.error(new RuntimeException("server 5XX error"));
                        })
                        .bodyToMono(MemberInfoDto.class).flatMap(member -> {
                            log.info("member id : {}", member.getId());
                            log.info("member email : {}", member.getEmail());
                            log.info("member name : {}", member.getName());
                            return next.handle(request);
                        })
                        .onErrorResume(result -> ServerResponse.badRequest().bodyValue(result.getMessage())))
                .onErrorResume(result -> ServerResponse.badRequest().bodyValue(result.getMessage()));
    }

    private Mono<Long> getMemberId(ServerRequest request){
        switch (request.path()){
            case "/schedule/save", "/schedule/update" -> {
                return request.bodyToMono(ScheduleDto.class)
                        .map(ScheduleDto::getMemberId);
            }
            case "/schedule/delete" -> {
                return request.bodyToMono(ScheduleRequestDto.class)
                        .map(ScheduleRequestDto::getMemberId);
            }
        }
        return Mono.error(new RuntimeException("path is not correct"));
    }
}
