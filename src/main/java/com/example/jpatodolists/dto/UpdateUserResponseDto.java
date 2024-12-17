package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//리스폰스 반환용
@Getter
public class UpdateUserResponseDto {
    private final String username;
    private final LocalDateTime createdAt;
    private final String email;

    public UpdateUserResponseDto(String username, LocalDateTime createdAt, String email) {
        this.username = username;
        this.createdAt = createdAt;
        this.email = email;
    }
}
