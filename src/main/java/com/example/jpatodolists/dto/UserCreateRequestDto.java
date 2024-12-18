package com.example.jpatodolists.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 50, message = "이메일은 50자 이내여야 합니다.")
    private String email;

    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    @Size(min = 2, max = 4, message = "사용자 이름은 2~4글자여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8~15자여야 합니다.")
    private String password;

    public UserCreateRequestDto(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
