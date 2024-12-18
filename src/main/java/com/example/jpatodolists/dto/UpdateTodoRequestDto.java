package com.example.jpatodolists.dto;

import lombok.Getter;

@Getter
public class UpdateTodoRequestDto {
    private final String newTitle;
    private final String newContent;

    public UpdateTodoRequestDto(String newTitle, String newContent) {
        this.newTitle = newTitle;
        this.newContent = newContent;
    }
}
