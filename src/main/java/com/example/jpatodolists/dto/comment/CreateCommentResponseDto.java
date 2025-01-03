package com.example.jpatodolists.dto.comment;

import com.example.jpatodolists.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class CreateCommentResponseDto {
    private final Long id;
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;

    public CreateCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}
