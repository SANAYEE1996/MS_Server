package com.ms.service;

import com.ms.entity.Color;
import com.ms.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorService {

    private  final ColorRepository colorRepository;

    public Mono<Color> findColor(Long id){
        return colorRepository.findById(id).flatMap(color -> {
            if(color == null){
                return Mono.error(RuntimeException::new);
            }
            return Mono.just(color);
        });
    }
}
