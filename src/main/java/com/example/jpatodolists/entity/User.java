package com.example.jpatodolists.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    // 생성자 매개변수 순서 수정: username, password, email
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void updateUser(String email, String username) {
        this.email = email;
        this.username = username;
    }


    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
