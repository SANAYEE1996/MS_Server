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

    @Bean
    public RouterFunction<?> routeProduct(ScheduleHandler handler){
        return route()
                .POST("/schedule/day", accept(MediaType.APPLICATION_JSON), handler::getDaySchedule)
                .POST("/schedule/month", accept(MediaType.APPLICATION_JSON), handler::getMonthSchedule)
                .POST("/schedule/save", accept(MediaType.APPLICATION_JSON), handler::saveSchedule)
                .POST("/schedule/delete", accept(MediaType.APPLICATION_JSON), handler::deleteSchedule)
                .POST("/schedule/update", accept(MediaType.APPLICATION_JSON), handler::updateSchedule)
                .POST("/schedule/check", accept(MediaType.APPLICATION_JSON), handler::check)
                .build();
    }
}
