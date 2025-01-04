package com.example.jpatodolists.entity;

import com.example.jpatodolists.exception.auth.UnauthorizedException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean isDeleted = false;

    /**
     * 사용자 생성을 위한 생성자
     * 각 필드의 유효성을 검증하고 초기화
     */
    public User(String username, String password, String email) {
        validateUserInfo(username, password, email);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * 사용자 정보 업데이트
     * 이메일과 사용자명 변경 시 유효성 검증 수행
     */
    public void update(String newEmail, String newUsername) {  // updateUser에서 update로 메서드명 변경
        validateUpdateInfo(newEmail, newUsername);
        this.email = newEmail;
        this.username = newUsername;
    }


    /**
     * 사용자와 연관된 Todo들의 소프트 삭제 처리
     */
    public void delete(List<Todo> todos) {
        validateDelete();
        this.isDeleted = true;
        // Todo 항목들도 함께 소프트 삭제 처리
        if (todos != null) {
            todos.forEach(Todo::markAsDeleted);  // setIsDeleted 대신 markAsDeleted 호출
        }
    }


    /**
     * 사용자 생성 시 필요한 정보 유효성 검증
     */
    private void validateUserInfo(String username, String password, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("사용자명은 필수 값입니다.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수 값입니다.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수 값입니다.");
        }
    }

    /**
     * 정보 업데이트 시 유효성 검증
     */
    private void validateUpdateInfo(String newEmail, String newUsername) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("이메일은 필수 값입니다.");
        }
        if (newUsername == null || newUsername.isBlank()) {
            throw new IllegalArgumentException("사용자명은 필수 값입니다.");
        }
    }

    /**
     * 삭제 가능 여부 검증
     */
    private void validateDelete() {
        if (this.isDeleted) {
            throw new IllegalStateException("이미 삭제된 사용자입니다.");
        }
    }
}