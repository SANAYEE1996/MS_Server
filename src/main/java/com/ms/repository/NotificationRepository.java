package com.ms.repository;

import com.ms.entity.Notification;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("notificationRepository")
public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {

    Flux<Notification> findByScheduleId(Long scheduleId);

    @Modifying
    Mono<Void> deleteByScheduleId(Long scheduleId);

}
