package com.ms.service;

import com.ms.entity.Color;
import com.ms.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public Color findColor(Long id){
        return colorRepository.findById(id).orElseThrow(()-> new RuntimeException(id+" is not existed color id"));
    }
}
