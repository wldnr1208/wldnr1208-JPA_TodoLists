package com.example.jpatodolists.dto.todo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateTodoResponseDto {
    private final String title;
    private final String content;
    private final LocalDateTime modifiedAt;
    private final Long id;


    public UpdateTodoResponseDto(Long id, String title, String content, LocalDateTime modifiedAt) {
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
        this.id = id;
    }
}
