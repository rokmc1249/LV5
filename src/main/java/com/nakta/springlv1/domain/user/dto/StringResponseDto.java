package com.nakta.springlv1.domain.user.dto;

import lombok.Getter;

@Getter
public class StringResponseDto {
    private String message;
    public StringResponseDto(String message) {
        this.message = message;
    }
}
