package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserCreateResponseDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    // 생성자 매개변수 순서를 필드 순서와 동일하게 수정
    public UserCreateResponseDto(Long id, String username, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }
}
