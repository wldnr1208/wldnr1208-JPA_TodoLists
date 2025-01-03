package com.example.jpatodolists.dto.user;

import com.example.jpatodolists.entity.User;
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

    // User 엔티티로부터 DTO를 생성하는 정적 팩토리 메서드
    public static UserCreateResponseDto from(User user) {
        return new UserCreateResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                "[PROTECTED]",  // 비밀번호는 보안을 위해 마스킹 처리
                user.getCreatedAt()
        );
    }
}
