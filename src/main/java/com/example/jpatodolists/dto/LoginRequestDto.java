package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class LoginRequestDto {
    private String email;
    private String password;
}
