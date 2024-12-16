package com.example.jpatodolists.dto;

import com.example.jpatodolists.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private String username;
    private String email;
    private LocalDateTime createdAt;

    public UserResponseDto(String username, LocalDateTime createdAt, String email) {
        this.username = username;
        this.createdAt = createdAt;
        this.email = email;
    }

    public static UserResponseDto toUser(User user) {
        return new UserResponseDto(user.getUsername(), user.getCreatedAt(), user.getEmail());
    }
}
