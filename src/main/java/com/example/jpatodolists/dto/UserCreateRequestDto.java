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
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    @Size(max = 4, message = "사용자 이름은 4글자 이내여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$",
            message = "비밀번호는 8~15자리이며, 적어도 하나의 문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    public UserCreateRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
