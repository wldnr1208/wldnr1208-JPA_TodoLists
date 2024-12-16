package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserCreateResponseDto {
    private Long id;               // 유저 ID
    private String username;       // 유저명
    private String email;          // 이메일
    private String password;
    private LocalDateTime createdAt; // 생성일

    public UserCreateResponseDto(Long id, String password, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.password = password;
    }
}
