package com.example.jpatodolists.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class LoginRequestDto {
    private String email;
    private String password;
}
