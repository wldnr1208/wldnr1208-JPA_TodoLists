package com.example.jpatodolists.dto.user;

import com.example.jpatodolists.entity.User;
import lombok.Getter;
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

    // User 엔티티로부터 DTO를 생성하는 정적 팩토리 메서드
    public static UpdateUserResponseDto from(User user) {
        return new UpdateUserResponseDto(
                user.getUsername(),
                user.getCreatedAt(),
                user.getEmail()
        );
    }
}
