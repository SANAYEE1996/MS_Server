package com.ms.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component("scheduleRouter")
@RequiredArgsConstructor
public class ScheduleRouter {

    private final JwtFilter jwtFilter;

    @Bean
    public RouterFunction<?> routeProduct(ScheduleHandler handler){
        return route()
                .POST("/schedule/day", accept(MediaType.APPLICATION_JSON), handler::getDaySchedule)
                .POST("/schedule/month", accept(MediaType.APPLICATION_JSON), handler::getMonthSchedule)
                .POST("/schedule/save", accept(MediaType.APPLICATION_JSON), handler::saveSchedule).filter(jwtFilter)
                .POST("/schedule/delete", accept(MediaType.APPLICATION_JSON), handler::deleteSchedule).filter(jwtFilter)
                .POST("/schedule/update", accept(MediaType.APPLICATION_JSON), handler::updateSchedule).filter(jwtFilter)
                .GET("/schedule/test", handler::check).filter(jwtFilter)
                .build();
    }
}
