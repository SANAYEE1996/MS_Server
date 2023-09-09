package com.ms.dto;

import lombok.Builder;

@Builder
public record ResponseDto(Integer code, String message, ResponseBody<?> body) {
}
