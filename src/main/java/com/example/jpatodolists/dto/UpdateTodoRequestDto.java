package com.example.jpatodolists.dto;

import lombok.Getter;

@Getter
public class UpdateTodoRequestDto {
    private final String newTitle;
    private final String newContent;

    public UpdateTodoRequestDto(String newContent, String newTitle) {
        this.newContent = newContent;
        this.newTitle = newTitle;
    }
}
