package com.ms.repository;

import com.ms.entity.Color;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("colorRepository")
public interface ColorRepository extends ReactiveCrudRepository<Color, Long> {
}
