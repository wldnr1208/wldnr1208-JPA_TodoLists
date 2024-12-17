package com.example.jpatodolists.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {

    private  String oldEmail;
    private  String newEmail;
    private  String oldUsername;
    private  String newUsername;

    public UpdateUserRequestDto(String oldEmail, String newEmail, String oldUsername, String newUsername) {
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
    }

}
