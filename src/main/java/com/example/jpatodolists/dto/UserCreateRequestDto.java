package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    private String username;
    private String email;
    private String password;

    public UserCreateRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
