package com.example.jpatodolists.dto;

import com.example.jpatodolists.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class TodoPageResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String username;

    public TodoPageResponseDto(Todo todo, Long commentCount) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.commentCount = commentCount;
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.username = todo.getUser().getUsername();
    }
}
