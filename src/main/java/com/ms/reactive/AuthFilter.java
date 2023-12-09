package com.ms.reactive;

import com.ms.dto.MemberInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class AuthFilter {

    @Value("${url.auth}")
    private String authUrl;

    public Mono<String> getBearerToken(ServerRequest request){
        List<String> list = request.headers().header("Authorization");
        if(list.isEmpty()){
            log.error("Jwt Token is not exist !");
            return Mono.error(new RuntimeException("Jwt Token is not exist !"));
        }
        String token = list.get(0);
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer")) {
            log.error("bearerToken is not exist !");
            return Mono.error(new RuntimeException("Jwt Token is not exist !"));
        }
        return Mono.just(token.substring(7));
    }

    public Mono<MemberInfoDto> authenticate(Long memberId, String bearerToken){
        WebClient webClient = WebClient.builder()
                .baseUrl(authUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.setBearerAuth(bearerToken))
                .build();

        return webClient.get().uri("/auth/info/{req}", memberId).retrieve()
                .onStatus(HttpStatusCode::is3xxRedirection, response -> Mono.error(new RuntimeException("server 3XX error")))
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error("4XX error :: Jwt token is not Valid");
                    return Mono.error(new RuntimeException("Jwt token is not Valid"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("auth server error 5XX");
                    return Mono.error(new RuntimeException("server 5XX error"));
                })
                .bodyToMono(MemberInfoDto.class);
    }
}
